package com.example.demo.forms;

import java.util.ArrayList;
import java.util.List;
import com.example.demo.entities.Products;

public class ChangeProductToProductForm {

  public static List<ProductsForms> changeToProductForm(List<Products> productList) {
    List<ProductsForms> productsFormsList = new ArrayList<>();
    for (Products products : productList) {
      ProductsForms product = ProductsForms.builder()
          .id(products.getProductId())
          .title(products.getTitle())  
          .Discription(products.getDiscription())
          .MRP(products.getMRP())
          .brand(products.getBrand().getBrandName())
          .discount(products.getDiscount())
          .imagePath1(products.getImages().getImage1())
          .imagePath2(products.getImages().getImage2())
          .imagePath3(products.getImages().getImage3())
          .imagePath4(products.getImages().getImage4())
          .imagePath5(products.getImages().getImage5())
          .returnPeriod(products.getReturnPeriod())
          .price(products.getPrice())
          .rating(products.getRating())
          .build();
      productsFormsList.add(product);
    }
    return productsFormsList;
  }

  public static ProductsForms changeToProductForm(Products products) {
      ProductsForms product = ProductsForms.builder()
          .id(products.getProductId())
          .title(products.getTitle())  
          .Discription(products.getDiscription())
          .MRP(products.getMRP())
          .brand(products.getBrand().getBrandName())
          .discount(products.getDiscount())
          .imagePath1(products.getImages().getImage1())
          .imagePath2(products.getImages().getImage2())
          .imagePath3(products.getImages().getImage3())
          .imagePath4(products.getImages().getImage4())
          .imagePath5(products.getImages().getImage5())
          .returnPeriod(products.getReturnPeriod())
          .price(products.getPrice())
          .rating(products.getRating())
          .build();
  
    return product;
  }

  public static List<Products> sortBySoldCount(List<Products> products){
    
    int n = products.size();

    for(int i=0;i<n;i++){
      for(int j=0;j<n-i;j++){
        // if( products.get(j).getSoldCount() != null && products.get(j).getSoldCount() < products.get(j+1).getSoldCount()){
        //     Products temp = products.get(j);
        //     products.add(j, products.get(j+1));
        //     products.add(j+1, temp);
        // }
      }
    }
    return products;
  }

}