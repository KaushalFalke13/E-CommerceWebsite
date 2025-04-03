package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.forms.ProductsForms;

@Controller
@RequestMapping("/Admin")
public class Admin {
  
  @RequestMapping("/addProducts")
  public String AddProducts(Model model) {
    ProductsForms ProductsForm = new ProductsForms();
    model.addAttribute("ProductsForm", ProductsForm);
    return "addProducts"; 
  }

  @RequestMapping("/Dashboard")
  public String Dashboard(){
    return "Dashboard";
  }

  @RequestMapping("/Products")
  public String Products(){
    
    return "Products";
  }

  @RequestMapping("/orders")
  public String orders(){
    
    return "orders";
  }

  @RequestMapping("/reports")
  public String reports(){
    
    return "reports";
  }
 
}
