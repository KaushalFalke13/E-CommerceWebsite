package com.example.demo.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.Repositories.AddressRepo;
import com.example.demo.Repositories.BagRepo;
import com.example.demo.Repositories.OrderRepo;
import com.example.demo.Repositories.OrdersProductsRepo;
import com.example.demo.Services.ProductsServicesImpl;
import com.example.demo.Services.UserserviceImpl;
import com.example.demo.entities.Address;
import com.example.demo.entities.BagCart;
import com.example.demo.entities.Orders;
import com.example.demo.entities.OrdersDetails;
import com.example.demo.entities.OrdersProducts;
import com.example.demo.entities.Products;
import com.example.demo.entities.Users;
import com.example.demo.forms.AddressForms;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.var;


@Controller
public class BagRequestController {

  @Autowired
  private UserserviceImpl userservice;

  @Autowired
  private ProductsServicesImpl ProductsServices;

  @Autowired
  private BagRepo bagRepo;

  @Autowired
  private AddressRepo addressRepo ;
  
 @Autowired
  private OrdersProductsRepo ordersProductsRepo;

  @Autowired
  private OrderRepo orderRepo;

  private Map<String,Object> allOrderDetailsMap = new HashMap<>();
  

  @RequestMapping(value = "/addToBag", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> addToBag(Principal principal, @RequestParam("productId") String productId) {
    String username = principal.getName();
    Users user = userservice.findByEmail(username);
    Products product = ProductsServices.SearchProductById(productId);
    Optional<BagCart> isbagCartPresent = bagRepo.findByUserAndProducts(user, product);

    if (isbagCartPresent.isPresent()) {
      System.out.println("Already product added");
    } else {
      BagCart bagItem = BagCart.builder()
          .user(user)
          .products(product)
          .build();
      bagRepo.save(bagItem);
    }
    Map<String, Object> response = new HashMap<>();
    List<BagCart> Baglist = bagRepo.findAllByUser(user);
    response.put("length", Baglist.size());
    return response;
  }

  @RequestMapping("/getBaglist")
  @ResponseBody
  public Map<String, Object> getBaglist(Principal principal) {
    if (principal == null) {
      return Collections.emptyMap();
    }
    String username = principal.getName();
    Users user = userservice.findByEmail(username);
    List<BagCart> Baglist = bagRepo.findAllByUser(user);
    Map<String, Object> response = new HashMap<>();
    response.put("length", Baglist.size());
    return response;
  }

  @RequestMapping(value = "/removeBaglistItem", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> removeBaglistItem(Principal principal, @RequestParam("productId") String productId) {
    Map<String, Object> response = new HashMap<>();
    String username = principal.getName();
    Users user = userservice.findByEmail(username);
    Products removedProduct = ProductsServices.SearchProductById(productId);
    Optional<BagCart> bagList = bagRepo.findByUserAndProducts(user, removedProduct);

    bagRepo.delete(bagList.get());

    List<Map<String, Object>> productsList = new ArrayList<>();
    List<BagCart> bagCarts = bagRepo.findAllByUser(user);
    for (BagCart cart : bagCarts) {
      Map<String, Object> productInfo = new HashMap<>();
      Products product = cart.getProducts();
      productInfo.put("productId", product.getProductId());
      productInfo.put("title", product.getTitle());
      productInfo.put("price", product.getPrice());
      productInfo.put("imagepath", product.getImages().getImage1());
      productInfo.put("discount", product.getDiscount());
      productInfo.put("MRP", product.getMRP());
      productInfo.put("brand", product.getBrand().getBrandName());
      productsList.add(productInfo);
    }
    response.put("length", bagCarts.size());
    response.put("productsList", productsList);
    return response;
  }

  

  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/getOrderdata", method = RequestMethod.POST)
  public String getOrderdata(Principal principal, @RequestParam("orderDetail") String orderDetailJson) throws Exception{
    ObjectMapper objectMapper = new ObjectMapper(); 
    Map<String, Object> orderDetail = objectMapper.readValue(orderDetailJson, Map.class);

    String username = principal.getName();
    allOrderDetailsMap.put(username,orderDetail);

    return "redirect:confirmOrder";
  }


  @RequestMapping("/confirmOrder")
  public String confirmOrder(Model model,Principal principal){ 
    String username = principal.getName();
    Users user = userservice.findByEmail(username);
    Address address = addressRepo.findByUser(user);
    model.addAttribute("address",address);
      return "confirmOrder";
    }
    
 
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<String> confirmOrder(RedirectAttributes redirectAttributes,Principal principal) throws JsonMappingException, JsonProcessingException{

    try{
    String username = principal.getName();
    
    Map<String, Object>  orderDetail = (Map<String, Object>)allOrderDetailsMap.get(username);

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
      Integer qty = Integer.parseInt(qtyStr);
      float price = products.getPrice() * qty;
      OrdersProducts ordersProducts = OrdersProducts.builder()
          .orders(orders)
          .products(products)
          .Quantity(qty)
          .price(price)
          .build();
      ordersProductsRepo.save(ordersProducts);
    }
    return ResponseEntity.ok("");
  }catch (Exception e) {
    return null;
  }
  }


@RequestMapping(value="/startPayment",method = RequestMethod.POST)
@ResponseBody
  public String startPayment(Principal principal) throws RazorpayException{

    try{
      String username = principal.getName();
      
      @SuppressWarnings("unchecked")
      Map<String, Object>  orderDetail = (Map<String, Object>)allOrderDetailsMap.get(username);

      Object totalAmountObj = orderDetail.get("TotalPRICE");
    float totalAmount = (totalAmountObj instanceof Number) ? ((Number) totalAmountObj).floatValue() : 0f;

    var client = new RazorpayClient("rzp_test_gSGPi3xiRwiLZa", 
    "SlzFsSWutHOCUmsoUdeXvYA1");


    JSONObject ob = new JSONObject();
    ob.put("amount", totalAmount*100);
    ob.put("currency", "INR");
    ob.put("receipt", "txn_12345");

     Order order = client.orders.create(ob);  
     return order.toString();
  
    }catch (Exception e){
      return null;
    }
  }


  @RequestMapping(value="/getUserAddress", method = RequestMethod.POST)
@ResponseBody
public ResponseEntity<AddressForms> saveAddress(Principal principal, @RequestBody AddressForms addressForm) {
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