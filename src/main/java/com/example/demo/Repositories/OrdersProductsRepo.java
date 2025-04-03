package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.OrdersProducts;

public interface OrdersProductsRepo extends JpaRepository<OrdersProducts,Integer>{

  @SuppressWarnings({ "null" })
  OrdersProducts save( OrdersProducts ordersProducts);

}
