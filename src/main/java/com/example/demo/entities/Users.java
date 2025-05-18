package com.example.demo.entities;


import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.enums.Status;
import com.example.demo.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Users implements UserDetails {

  @Id
  private String userid;
  private String name;
  private String email;
  @Getter(value = AccessLevel.NONE)
  private String password ;
  private Integer phone_Number;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private Status status=Status.ACTIVE;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING) 
  private List<Roles> roles;

  @OneToMany(mappedBy = "user")
  private List<Address> address;
  
  @OneToMany(mappedBy = "user")
  private Set<WatchListCart> watchListCarts;

  @OneToMany(mappedBy = "user")
  private Set<BagCart> bagCart;
 
  @OneToMany(mappedBy = "user")
  private List<Orders> orders;

  @Override
  public String getUsername() {
  return this.email;
  }

  @Override
  public String getPassword() {
   return this.password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }


  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getPhone_Number() {
    return phone_Number;
  }

  public void setPhone_Number(Integer phone_Number) {
    this.phone_Number = phone_Number;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public List<Roles> getRoles() {
    return roles;
  }

  public void setRoles(List<Roles> roles) {
    this.roles = roles;
  }

  public List<Address> getAddress() {
    return address;
  }

  public void setAddress(List<Address> address) {
    this.address = address;
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

  public List<Orders> getOrders() {
    return orders;
  }

  public void setOrders(List<Orders> orders) {
    this.orders = orders;
  }
}
