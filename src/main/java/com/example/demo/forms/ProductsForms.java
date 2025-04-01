package com.example.demo.forms;


import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.rating;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductsForms {

  private String id;
  private MultipartFile[] images;
  private String brand;
  private String title;
  private String Category;
  private String SubCategory;
  private String productType;
  private Integer MRP;
  private Float price;
  private Integer stock;
  private Integer discount;
  private Integer returnPeriod;
  private String Discription;
  private rating rating;
  private String imagePath1;
  private String imagePath2;
  private String imagePath3;
  private String imagePath4;
  private String imagePath5;
  private String keyword1;
  private String keyword2;
  private String keyword3;
  private String keyword4;
  private String keyword5;

}

