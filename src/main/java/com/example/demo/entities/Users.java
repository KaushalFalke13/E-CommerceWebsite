package com.example.demo.entities;


import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.enums.Status;
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
    System.out.println(password);
   return this.password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }






}
