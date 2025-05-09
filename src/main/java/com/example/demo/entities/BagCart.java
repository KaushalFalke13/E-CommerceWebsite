package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
public class BagCart {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer BagId;

  @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id") 
    private Products products;

}
