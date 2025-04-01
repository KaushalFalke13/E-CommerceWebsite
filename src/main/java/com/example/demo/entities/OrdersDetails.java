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

}
