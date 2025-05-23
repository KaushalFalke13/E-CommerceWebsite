package com.example.demo.Services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Repositories.CatagoriesRepo;
import com.example.demo.Repositories.ProductsRepo;
import com.example.demo.entities.Categories;
import com.example.demo.entities.Products;
import com.example.demo.enums.ProductType;
import com.example.demo.forms.ChangeProductToProductForm;
import com.example.demo.forms.ProductsForms;

import jakarta.transaction.Transactional;

@Service
public class ProductsServicesImpl implements ProductsServices{

  @Autowired
  private ProductsRepo productsRepo;

  @Autowired
  private CatagoriesRepo catagoriesRepo;


  @Override
  public List<Products> SearchProducts(String query) {
    return productsRepo.findByKeywordContaining(query);
  }


  public List<Products> SearchByProductType(ProductType productType){
    return  productsRepo.findBycategory_productType(productType);
  }


  public Products SearchBySlug(String slug){
    return productsRepo.findBySlug(slug);
  }


  @Override
  public Categories SaveCatagory(Categories catagory) {
   return catagoriesRepo.save(catagory);
  }

  @Override
  public Products SearchProductById(String id) {
    return productsRepo.findByproductId(id);
  }

  @Override
  public Products SaveProducts(Products Product) {
    return productsRepo.save(Product);
  }

  @Override
  public List<Products> getAllProducts() {
    return productsRepo.findAll();
  }

  public List<Products> SortByPriceATD() {
    return productsRepo.findAllByOrderByPriceAsc();
  }

  public List<Products> SortByPriceDTA() {
    return productsRepo.findAllByOrderByPriceDesc();
  }
//
//  public List<Products> SortByNewProducts() {
//    return productsRepo.findAllByOrderByPriceDesc();
//  }
//
//  public List<Products> SortByRating() {
//    return productsRepo.findAllByOrderByRatingCountDesc();
//  }

  public List<Products> SortbyDiscount() {
    return productsRepo.findAllByOrderByDiscountDesc();
  }

  // @Override
  // public List<Products> findByBrand(String BrandName) {
  //   return productsRepo.findAllByBrand(BrandName);
  // }

  @Override
  public List<Products> findByPriceBetween(double MinPrice, double MaxPrice) {
    return productsRepo.findAllByPriceBetween(MinPrice, MaxPrice);
  }

  @Override
  public List<Products> findByDiscount(int Discount) {
  return productsRepo.findAllByDiscountGreaterThanEqual(Discount);
  }


  @Override
  @Transactional
  public Boolean updateProductStock(String Id ,int qty) {
    Optional<Products> product = productsRepo.findByproductIdForUpdate(Id);
    int stock = product.get().getStock();
    if(stock <=0 || stock -qty <= -1){
      return false;
    }
      product.get().setStock(stock-qty);
    return true; 
  }


  @Override
  public List<ProductsForms> getProductsByIds(String[] productIds) {
    List<Products> ProductList = new ArrayList<>();
    for (String element : productIds) {
      ProductList.add(SearchProductById(element));
    }
    return ChangeProductToProductForm.changeToProductForm(ProductList); 
  }


  @Override
  public ProductsForms getProductById(String id) {
    Products Product = SearchProductById(id);
    return ChangeProductToProductForm.changeToProductForm(Product);
  }

}

