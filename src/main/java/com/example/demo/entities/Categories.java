package com.example.demo.entities;

import java.util.List;

import com.example.demo.enums.CategoryTypes;
import com.example.demo.enums.ProductType;
import com.example.demo.enums.SubCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Entity
public class Categories {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // private Integer returnPeriod;
    
    @Enumerated(EnumType.STRING)
    private CategoryTypes catagory_type;  

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_catagory", length = 255)
    private  SubCategory  sub_catagory;

    @Enumerated(EnumType.STRING)
    @Column(name = "Product_type")
    private  ProductType  productType;

    
    @OneToMany(mappedBy = "category")
    private List<Products> products;

}
