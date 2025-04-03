package com.example.demo.Repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Products;
import com.example.demo.entities.Users;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepo  extends JpaRepository<Users,Integer>{

  Users findByEmail(String email);  

  @Modifying
    @Transactional
    @Query("UPDATE WatchListCart w SET w.products = :products WHERE w.CartId = :cartId")
    void updateProductsSet(@Param("products") Set<Products> products, @Param("cartId")Integer cartId);

  @SuppressWarnings({ "null" })
  Users save(Users users);
}
