package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer addressId;

  private String street;
  private String city;
  private String state;
  private Integer pinCode;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private Users user;
}
