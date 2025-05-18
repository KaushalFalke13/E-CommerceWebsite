package com.example.demo.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.BagCart;
import com.example.demo.entities.Products;
import com.example.demo.entities.Users;

@Repository
public interface BagRepo extends JpaRepository<BagCart,Integer>{

  @SuppressWarnings({ "null", "unchecked"})
  BagCart save(BagCart bagCart);

  BagCart findByUser(Users user);

  Optional<BagCart> findByUserAndProducts(Users user, Products product);

  List<BagCart> findAllByUser(Users user);

} 
