package com.example.demo.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Admin implements UserDetails { 

  @Id
  private String adminId;
  private String name;
  private String email;
  @Getter(value = AccessLevel.NONE)
  private String password ;
  private Integer phone_Number;

  @OneToMany(mappedBy = "admin") 
  private List<OrdersProducts> ordersProducts;
  
  @OneToMany(mappedBy = "admin")
  private List<Products> product;
  
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


  public String getAdminId() {
    return adminId;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
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
}
