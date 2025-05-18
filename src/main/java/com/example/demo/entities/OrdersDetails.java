package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrdersDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private LocalTime orderTime;
  private LocalDate orderDate;
  private float TotalMRP;
  private float TotalDoscount;
  private float TotalAmount;
  
  @OneToOne(mappedBy = "ordersDetails") 
  @JoinColumn(name = "OrderId") 
  private Orders orders;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalTime getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(LocalTime orderTime) {
    this.orderTime = orderTime;
  }

  public LocalDate getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
  }

  public float getTotalMRP() {
    return TotalMRP;
  }

  public void setTotalMRP(float totalMRP) {
    TotalMRP = totalMRP;
  }

  public float getTotalDoscount() {
    return TotalDoscount;
  }

  public void setTotalDoscount(float totalDoscount) {
    TotalDoscount = totalDoscount;
  }

  public float getTotalAmount() {
    return TotalAmount;
  }

  public void setTotalAmount(float totalAmount) {
    TotalAmount = totalAmount;
  }

  public Orders getOrders() {
    return orders;
  }

  public void setOrders(Orders orders) {
    this.orders = orders;
  }
}
