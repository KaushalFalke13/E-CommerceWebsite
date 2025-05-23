package com.example.demo.Services;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.demo.Repositories.AddressRepo;
import com.example.demo.Repositories.OrderRepo;
import com.example.demo.Repositories.OrdersProductsRepo;
import com.example.demo.entities.Address;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Orders;
import com.example.demo.entities.OrdersDetails;
import com.example.demo.entities.OrdersProducts;
import com.example.demo.entities.Products;
import com.example.demo.entities.Users;
import com.example.demo.forms.AddressForms;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import jakarta.transaction.Transactional;

public class OrdersServiceImpl implements OrdersService {

  private Map<String, Object> allOrderDetailsMap = new HashMap<>();

  @Autowired
  private UserserviceImpl userservice;

  @Autowired
  private ProductsServicesImpl ProductsServices;

  @Autowired
  private AddressRepo addressRepo;

  @Autowired
  private OrdersProductsRepo ordersProductsRepo;

  @Autowired
  private OrderRepo orderRepo;

  @Override
  public String startOrderPayments(Principal principal) {
    try {
      String username = principal.getName();

      @SuppressWarnings("unchecked")
      Map<String, Object> orderDetail = (Map<String, Object>) allOrderDetailsMap.get(username);

      Object totalAmountObj = orderDetail.get("TotalPRICE");
      float totalAmount = (totalAmountObj instanceof Number) ? ((Number) totalAmountObj).floatValue() : 0f;

      var client = new RazorpayClient("rzp_test_gSGPi3xiRwiLZa",
          "SlzFsSWutHOCUmsoUdeXvYA1");

      JSONObject ob = new JSONObject();
      ob.put("amount", totalAmount * 100);
      ob.put("currency", "INR");
      ob.put("receipt", "txn_12345");

      Order order = client.orders.create(ob);
      return order.toString();

    } catch (Exception e) {
      return null;
    }
  }

  @Transactional
  @SuppressWarnings("unchecked")
  @Override
  public ResponseEntity<String> confirmOrders(Principal principal) {
    try {
      String username = principal.getName();
      Map<String, Object> orderDetail = (Map<String, Object>) allOrderDetailsMap.get(username);

      Users user = userservice.findByEmail(username);
      Object totalMRPObj = orderDetail.get("TotalMRP");
      Object productIdObj = orderDetail.get("product");
      Object QtyObj = orderDetail.get("qty");

      Object totalAmountObj = orderDetail.get("TotalPRICE");
      float totalMRP = (totalMRPObj instanceof Number) ? ((Number) totalMRPObj).floatValue() : 0f;
      float totalAmount = (totalAmountObj instanceof Number) ? ((Number) totalAmountObj).floatValue() : 0f;

      List<String> productsID = (productIdObj instanceof List) ? ((List<String>) productIdObj) : new ArrayList<>();
      List<String> Qty = (QtyObj instanceof List) ? ((List<String>) QtyObj) : new ArrayList<>();
      Integer ordernumber = Optional.ofNullable(orderRepo.findLastOrderNumber()).orElse(0);
      float TotalDiscounts = (totalMRP - totalAmount);
      Orders orders = Orders.builder()
          .user(user)
          .OrderNumber(++ordernumber)
          .TotalAmount(totalAmount)
          .ordersDetails(OrdersDetails.builder()
              .orderDate(LocalDate.now())
              .orderTime(LocalTime.now())
              .TotalMRP(totalMRP)
              .TotalDoscount(TotalDiscounts)
              .TotalAmount(totalAmount)
              .build())
          .build();
      orderRepo.save(orders);

      for (int index = 0; index < productsID.size(); index++) {
        String productId = productsID.get(index);
        String qtyStr = Qty.get(index);
        Products products = ProductsServices.SearchProductById(productId);
        Admin adminName = products.getAdmin();
        Integer qty = Integer.parseInt(qtyStr);
        ProductsServices.updateProductStock(productId, qty);
        float price = products.getPrice() * qty;
        OrdersProducts ordersProducts = OrdersProducts.builder()
            .orders(orders)
            .products(products)
            .Quantity(qty)
            .price(price)
            .admin(adminName)
            .build();
        ordersProductsRepo.save(ordersProducts);
      }
      return ResponseEntity.ok("Order Placed Successfully");
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void getOrderData(Principal principal, String orderDetailJson) throws JsonProcessingException {

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> orderDetail = objectMapper.readValue(orderDetailJson, Map.class);

    String username = principal.getName();
    allOrderDetailsMap.put(username, orderDetail);

  }

  @Override
  public ResponseEntity<AddressForms> saveNewAddress(Principal principal, AddressForms addressForm) {
    try {
      String username = principal.getName();
      Users user = userservice.findByEmail(username);

      Address newaddress = Address.builder()
          .street(addressForm.getStreet())
          .city(addressForm.getCity())
          .state(addressForm.getState())
          .pinCode(addressForm.getPinCode())
          .user(user)
          .build();

      addressRepo.save(newaddress);
      return ResponseEntity.ok(addressForm);
    } catch (Exception e) {
      return null;
    }
  }

}