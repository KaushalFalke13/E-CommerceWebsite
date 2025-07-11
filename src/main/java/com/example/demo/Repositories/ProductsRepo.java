package com.example.demo.Repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Products;
import com.example.demo.enums.ProductType;
import jakarta.persistence.LockModeType;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Integer> {

  @SuppressWarnings({ "null", "unchecked" })
  Products save(Products products);

  @SuppressWarnings("null")
  List<Products> findAll();

  @Query("SELECT DISTINCT p FROM Products p JOIN p.searchKeyword k " +
      "WHERE LOWER(k.keyword) LIKE LOWER(CONCAT('%', :kw, '%'))")
  List<Products> findByKeywordContaining(@Param("kw") String keyword);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT p FROM Products p WHERE p.productId = :productId")
  Optional<Products> findByproductIdForUpdate(@Param("productId") String productId);

  List<Products> findBycategory_productType(ProductType productType);

  Products findBySlug(String slug);

  List<Products> findAllByOrderByPriceDesc();

  List<Products> findAllByOrderByPriceAsc();

  List<Products> findAllByOrderByDiscountDesc();

  List<Products> findAllByOrderByRatingCountDesc();

  Products findByproductId(String id);

  List<Products> findAllByPriceBetween(double MinPrice, double MaxPrice);

  List<Products> findAllByDiscountGreaterThanEqual(double Discount);

}
