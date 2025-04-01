package com.example.demo.Repositories;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Products;
import com.example.demo.entities.Users;
import com.example.demo.entities.WatchListCart;

@Repository
public interface WatchlistRepo  extends JpaRepository<WatchListCart,Integer>{

  @SuppressWarnings({ "null", "unchecked" })
  WatchListCart save(WatchListCart watchListCart);

   
//  @Modifying
//     @Query("UPDATE WatchListCart SET products = :products WHERE cartId = :cartId")
//     void updateProductsSet(Set<Products> products, Integer cartId);

  void deleteByUserAndProducts(Users user, Products product); 

  

    WatchListCart findByUser(Users user);

    Optional<WatchListCart> findByUserAndProducts(Users user, Products product);

    List<WatchListCart> findAllByUser(Users user);
  }
