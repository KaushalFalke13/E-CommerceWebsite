package com.example.demo.controllers;


import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Services.ProductsServicesImpl;
import com.example.demo.entities.*;
import com.example.demo.forms.ProductsForms;

@Controller
public class ProductRequestController {

  @Autowired
  private ProductsServicesImpl ProductsServices;


//   Display Particular Product
  @RequestMapping(value = "/{slug}", method = RequestMethod.GET)
  private String viewProducts(@PathVariable String slug, Model model) {
    Products product = ProductsServices.SearchBySlug(slug);
    // ProductType productType = product.getCategory().getProductType();
    // System.out.println(product.getCategory());
    // List<Products> ListOfSimilarProduct = ProductsServices.SearchByProductType(productType);
    // model.addAttribute("ListOfSimilarProduct", ListOfSimilarProduct);
    model.addAttribute("Product", product);
    return "ViewProduct";
  }


  // Get Product by ProductID
  @ResponseBody
  @RequestMapping(value = "/getProductByIds", method = RequestMethod.POST)
  public Map<String, Object> getProductByIds(@RequestParam("productId") String[] productIds) {
    List<ProductsForms> productsForms = ProductsServices.getProductsByIds(productIds);
    Map<String, Object> response = new HashMap<>();
    response.put("productsForms", productsForms);
    return response;
  }

  @RequestMapping(value = "/getProductById", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> getProductById(@RequestParam("productId") String productId) {
    ProductsForms productform = ProductsServices.getProductById(productId);
    Map<String, Object> response = new HashMap<>();
    response.put("productform", productform);
    return response;
  } 
}

