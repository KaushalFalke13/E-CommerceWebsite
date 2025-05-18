package com.example.demo.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Admin;
import com.example.demo.entities.OrdersProducts;
import com.example.demo.enums.OrderStatus;

public interface OrdersProductsRepo extends JpaRepository<OrdersProducts,Integer>{

  @SuppressWarnings({ "null", "unchecked" })
  OrdersProducts save( OrdersProducts ordersProducts);

  @Query("SELECT o FROM OrdersProducts o WHERE o.admin = :admin AND o.status = :status")
  List<OrdersProducts> findOrderByAdminAndStatus(@Param("admin") Admin admin,@Param("status") OrderStatus status);

}
