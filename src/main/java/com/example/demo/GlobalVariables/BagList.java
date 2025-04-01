package com.example.demo.GlobalVariables;


import org.springframework.stereotype.Service;


@Service
public class BagList {
 
  public Integer OrderNumber = 1;

  
  public Integer getOrderNumber() {
    return OrderNumber;
  }

  public void setOrderNumber(Integer orderNumber) {
    OrderNumber = orderNumber;
  }
}
