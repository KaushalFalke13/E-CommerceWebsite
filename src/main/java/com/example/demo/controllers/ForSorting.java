package com.example.demo.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Services.ProductsServicesImpl;
import com.example.demo.entities.Products;
import com.example.demo.entities.searchKeywords;
import com.example.demo.forms.ChangeProductToProductForm;
import com.example.demo.forms.ProductsForms;
import jakarta.servlet.http.HttpSession;

@Controller
public class ForSorting {

@Autowired
private ProductsServicesImpl ProductsServices;

  @RequestMapping("/searchProducts")  
  @ResponseBody
  public Set<String> searchProducts(@RequestParam String query,HttpSession session) {
    List<Products> result = ProductsServices.SearchProducts(query);
    Set<String> searchResult = new HashSet<>();
    for (Products product : result) {
      Set<searchKeywords> keywords = product.getSearchKeyword();
      
      if (keywords != null) {
          for (searchKeywords keyword : keywords) {
            if(keyword.getKeyword().contains(query)){
                searchResult.add(keyword.getKeyword()); 
            }
          }
      }
    }
  return searchResult;
  }

  @RequestMapping("/getSearchedProducts")  
  @ResponseBody
  public List<ProductsForms> getSearchedProducts(@RequestParam String query,HttpSession session) {
    List<Products> result = ProductsServices.SearchProducts(query);   
    session.setAttribute("currentProductList", result);
    List<Products> resultList = ChangeProductToProductForm.sortBySoldCount(result);
    List<ProductsForms> productsResult = ChangeProductToProductForm.changeToProductForm(resultList);
    return productsResult;
  }

  
@SuppressWarnings("unchecked")
@RequestMapping(value = "/getProductsByBrand",method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getProductsByBrand(@RequestParam("BrandName") List<String> BrandName,HttpSession session){ 
  List<Products> newProdcutsListByBrand = new ArrayList<>();
        List<Products> ProductList = (List<Products>) session.getAttribute("currentProductList");

    for (Products element : ProductList) {
      String brand = element.getBrand().getBrandName(); 
      if(BrandName.contains(brand)){
        newProdcutsListByBrand.add(element);
      }
   }
  List<Products> resultList = ChangeProductToProductForm.sortBySoldCount(newProdcutsListByBrand);
  List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(resultList);
  Map<String, Object> response=new HashMap<>();
  response.put("productsForms", productsForms);
  return response;
}


@SuppressWarnings("unchecked")
@RequestMapping(value = "/getProductsByDiscount",method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getProductsByDiscount(@RequestParam("DiscountPercent") int Discount,HttpSession session){ 

  List<Products> newProductsListByDiscount = new ArrayList<>();
  List<Products> ProductList = (List<Products>) session.getAttribute("currentProductList");

  for (Products element : ProductList) {
    if(element.getDiscount() >= Discount){
      newProductsListByDiscount.add(element);
    }
  }
List<Products> resultList = ChangeProductToProductForm.sortBySoldCount(newProductsListByDiscount);
List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(resultList);
Map<String, Object> response=new HashMap<>();
response.put("productsForms", productsForms);
  return response;
} 


@SuppressWarnings("unchecked")
@RequestMapping(value = "/getProductBetweenPrice",method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getProductBetweenPrice(@RequestParam("min") double min, @RequestParam("max") double max,HttpSession session){ 
 
List<Products> ProductList = (List<Products>) session.getAttribute("currentProductList");

  List<Products> newProductsListByPrice = new ArrayList<>();
  for (Products element : ProductList) {
    if(element.getPrice() <= max && element.getPrice() >= min){
      newProductsListByPrice.add(element);
    }
  }
List<Products> resultList = ChangeProductToProductForm.sortBySoldCount(newProductsListByPrice);
List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(resultList);
Map<String, Object> response=new HashMap<>();
response.put("productsForms", productsForms);
  return response;
}


@SuppressWarnings("unchecked")
@RequestMapping("/Recommended") 
@ResponseBody
public Map<String, Object> Recommended(HttpSession session){

  List<Products> ProductList = (List<Products>) session.getAttribute("currentProductList");
  List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(ProductList);
  Map<String, Object> response=new HashMap<>();
  response.put("productsForms", productsForms);
  return response;
}














@RequestMapping("/WhatsNew")
@ResponseBody
public Map<String, Object> WhatsNew(HttpSession session){

  List<Products> newProductsListByPrice = new ArrayList<>();
  //  Sort by create Date 
  List<Products> resultList = ChangeProductToProductForm.sortBySoldCount(newProductsListByPrice);
  List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(resultList);
  Map<String, Object> response=new HashMap<>();
  response.put("productsForms", productsForms);
  return response;
}


@RequestMapping("/Popularity")
@ResponseBody
public Map<String, Object> Popularity(HttpSession session){
  // Sort by rating
  // List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(ProductList);
  Map<String, Object> response=new HashMap<>();
  // response.put("productsForms", productsForms);
  return response;
}


@RequestMapping("/sortbyDiscount")
@ResponseBody
public Map<String, Object> sortbyDiscount(HttpSession session){
    List<Products> ProductList =  ProductsServices.SortbyDiscount();
    //Sort By discount
    List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(ProductList);
    Map<String, Object> response=new HashMap<>();
    response.put("productsForms", productsForms);
    return response;
}


@RequestMapping("/sortDesc")
@ResponseBody
public Map<String, Object> showProductInDesc(HttpSession session){
        List<Products> ProductList =  ProductsServices.SortByPriceDTA();
        // Sort Product by Price max
         List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(ProductList);
        Map<String, Object> response=new HashMap<>();
        response.put("productsForms", productsForms);
        return response;
}


@RequestMapping("/sortAsc")
@ResponseBody
public Map<String, Object> showProductInAsc(HttpSession session){
       List<Products> ProductList =  ProductsServices.SortByPriceATD();
       // Sort Product by Price min
       List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(ProductList);
       Map<String, Object> response=new HashMap<>();
       response.put("productsForms", productsForms);
       return response;
}

} 