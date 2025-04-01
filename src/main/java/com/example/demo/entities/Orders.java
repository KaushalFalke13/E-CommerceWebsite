package com.example.demo.entities;

import java.util.List;

import com.example.demo.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer Id;

  @ManyToOne 
  @JoinColumn(name = "UserId")
  private Users user;

  @Column(unique = true)
  private Integer OrderNumber; 

  private float TotalAmount; 
  
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private OrderStatus status = OrderStatus.PENDING;

  @OneToOne(cascade = CascadeType.ALL)
  private OrdersDetails ordersDetails;

  @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
  private List<OrdersProducts> ordersProducts;
}
