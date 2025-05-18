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

  @ManyToOne(cascade = CascadeType.ALL)
  private Admin admin;

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


  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Images getImages() {
    return images;
  }

  public void setImages(Images images) {
    this.images = images;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getMRP() {
    return MRP;
  }

  public void setMRP(Integer MRP) {
    this.MRP = MRP;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public Integer getDiscount() {
    return discount;
  }

  public void setDiscount(Integer discount) {
    this.discount = discount;
  }

  public Integer getSoldCount() {
    return soldCount;
  }

  public void setSoldCount(Integer soldCount) {
    this.soldCount = soldCount;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public Integer getReturnPeriod() {
    return returnPeriod;
  }

  public void setReturnPeriod(Integer returnPeriod) {
    this.returnPeriod = returnPeriod;
  }

  public String getDiscription() {
    return Discription;
  }

  public void setDiscription(String discription) {
    Discription = discription;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Admin getAdmin() {
    return admin;
  }

  public void setAdmin(Admin admin) {
    this.admin = admin;
  }

  public Status getProductStatus() {
    return ProductStatus;
  }

  public void setProductStatus(Status productStatus) {
    ProductStatus = productStatus;
  }

  public com.example.demo.entities.rating getRating() {
    return rating;
  }

  public void setRating(com.example.demo.entities.rating rating) {
    this.rating = rating;
  }

  public Brands getBrand() {
    return brand;
  }

  public void setBrand(Brands brand) {
    this.brand = brand;
  }

  public Categories getCategory() {
    return category;
  }

  public void setCategory(Categories category) {
    this.category = category;
  }

  public Set<searchKeywords> getSearchKeyword() {
    return searchKeyword;
  }

  public void setSearchKeyword(Set<searchKeywords> searchKeyword) {
    this.searchKeyword = searchKeyword;
  }

  public Set<WatchListCart> getWatchListCarts() {
    return watchListCarts;
  }

  public void setWatchListCarts(Set<WatchListCart> watchListCarts) {
    this.watchListCarts = watchListCarts;
  }

  public Set<BagCart> getBagCart() {
    return bagCart;
  }

  public void setBagCart(Set<BagCart> bagCart) {
    this.bagCart = bagCart;
  }

  public List<OrdersProducts> getOrdersProducts() {
    return ordersProducts;
  }

  public void setOrdersProducts(List<OrdersProducts> ordersProducts) {
    this.ordersProducts = ordersProducts;
  }
}
