package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.entities.Orders;
import com.example.demo.entities.Users;


public interface OrderRepo extends JpaRepository<Orders,Integer>{

  @SuppressWarnings({ "null", "unchecked" })
  Orders save( Orders order);
  
  @Query(value = "SELECT order_number FROM orders ORDER BY id DESC LIMIT 1", nativeQuery = true)
  Integer findLastOrderNumber();

  List<Orders> findAllByUser(Users user);
} 
