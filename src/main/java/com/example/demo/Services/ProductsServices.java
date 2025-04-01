package com.example.demo.Services;

import java.util.List;

import com.example.demo.entities.Categories;
import com.example.demo.entities.Products;

public interface ProductsServices {

  List<Products> SearchProducts(String query);
  
  Products SaveProducts( Products Product);

  Categories SaveCatagory(Categories catagory);
  
  List<Products> getAllProducts();

  List<Products> SortByPriceATD();

  List<Products> SortByPriceDTA();

  List<Products> findByPriceBetween(double MinPrice ,double MaxPrice);

  List<Products> findByDiscount(int Discount);

  Products SearchProductById(String id);
  
  

}
