package com.example.demo.forms;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderForm {
  private Integer OrderId;
  private LocalTime orderTime;
  private String UserName;
  private LocalDate orderDate;
  private float TotalMRP;
  private OrderStatus status;
  private float TotalAmount;
  private ProductsForms products;
  private Integer Quantity;

}
