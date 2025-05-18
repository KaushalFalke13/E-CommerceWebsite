package com.example.demo.forms;


import java.util.ArrayList;
import java.util.List;
import com.example.demo.entities.OrdersProducts;


public class ChangeOrderToOrderForms{

    public static List<OrderForm> changeToOrderForm(List<OrdersProducts> ordersProducts){
        List<OrderForm> orderFormsList = new ArrayList<>();
        
        for (OrdersProducts element : ordersProducts) {
            ProductsForms productsform = ChangeProductToProductForm.changeToProductForm(element.getProducts());
            OrderForm order = OrderForm.builder()
          .OrderId(element.getOrders().getId())
          .orderTime(element.getOrders().getOrdersDetails().getOrderTime())  
          .UserName(element.getOrders().getUser().getName())
          .orderDate(element.getOrders().getOrdersDetails().getOrderDate())
          .TotalMRP(element.getProducts().getMRP())
          .status(element.getOrders().getStatus())
          .TotalAmount(element.getProducts().getPrice())
          .products(productsform)
          .Quantity(element.getQuantity())
          .build();
          orderFormsList.add(order);
    }
        
        return orderFormsList;
    }
}
