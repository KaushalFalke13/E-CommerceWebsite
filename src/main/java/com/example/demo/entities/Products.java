package com.example.demo.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.demo.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity(name = "Products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonIgnoreProperties({"bagCart","watchListCarts"})
public class Products {

  @Id  
  private String productId;

  @OneToOne(cascade = CascadeType.ALL)
  private Images images; 
  private String title;
  private Integer MRP;
  private float price;
  private Integer stock;
  private Integer discount;
  private Integer soldCount;
  @Column(unique = true)
  private String slug;

  @Builder.Default
  private Integer returnPeriod = 7;
  @Column(length = 1000)
  private String Discription;

  private String color;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private Status ProductStatus = Status.ACTIVE;
 
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "rating_id")
  private rating rating;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "brand_id") 
  private Brands brand; 

  
  @JsonManagedReference
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cat_id")  
  private Categories category;

  @ManyToMany(cascade = {CascadeType.ALL, CascadeType.MERGE })
  @Builder.Default
  @JoinTable(name = "Products_Keywords",
  joinColumns = @JoinColumn(name = "Product_id"),
  inverseJoinColumns = @JoinColumn  (name="searchKeywords_id"))  
  private Set<searchKeywords> searchKeyword = new HashSet<>();
 
  @JsonManagedReference
  @OneToMany(mappedBy = "products")
  private Set<WatchListCart> watchListCarts;

  @JsonManagedReference
  @OneToMany(mappedBy = "products")  
  private Set<BagCart> bagCart;

  @OneToMany(mappedBy = "products") 
  private List<OrdersProducts> ordersProducts;

} 
