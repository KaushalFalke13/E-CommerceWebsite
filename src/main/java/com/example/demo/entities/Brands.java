package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Brands {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Integer brandId;

private String brandName;

 @OneToMany(mappedBy = "brand")
    private List<Products> products;
}
