package com.example.demo.Services;

import java.security.Principal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.Repositories.BagRepo;
import com.example.demo.entities.BagCart;
import com.example.demo.entities.Products;
import com.example.demo.entities.Users;

public class BagServiceImpl implements BagService{

  @Autowired
  private UserserviceImpl userservice;

  @Autowired
  private ProductsServicesImpl ProductsServices;

  @Autowired
  private BagRepo bagRepo;

  @Override
  public Map<String, Object> addProductToBag(Principal principal,String productId) {
     
    String username = principal.getName();
    Users user = userservice.findByEmail(username);
    Products product = ProductsServices.SearchProductById(productId);
    Optional<BagCart> isProductAlreadyPresent = bagRepo.findByUserAndProducts(user, product);

    if (isProductAlreadyPresent.isPresent()) {
      System.out.println("Already product added");
    } else {
      BagCart bagItem = BagCart.builder()
          .user(user)
          .products(product)
          .build();
      bagRepo.save(bagItem);
    }
    
    Map<String, Object> response = new HashMap<>();
    List<BagCart> Baglist = bagRepo.findAllByUser(user);
    response.put("length", Baglist.size());
    return response;
  }

  @Override
  public Map<String, Object> removeProductFromBag(Principal principal,String productId) {
    
    String username = principal.getName();
    Users user = userservice.findByEmail(username);
    Products removedProduct = ProductsServices.SearchProductById(productId);
    Optional<BagCart> bagList = bagRepo.findByUserAndProducts(user, removedProduct);
    bagRepo.delete(bagList.get());

    return getBagCartList(principal);
  }

  @Override
  public Map<String, Object> getBagCartList(Principal principal) {
    
    String username = principal.getName();
    Users user = userservice.findByEmail(username);
    List<Map<String, Object>> productsList = new ArrayList<>();
    List<BagCart> bagCarts = bagRepo.findAllByUser(user);
    for (BagCart cart : bagCarts) {
      Map<String, Object> productInfo = new HashMap<>();
      Products product = cart.getProducts();
      productInfo.put("productId", product.getProductId());
      productInfo.put("title", product.getTitle());
      productInfo.put("price", product.getPrice());
      productInfo.put("imagepath", product.getImages().getImage1());
      productInfo.put("discount", product.getDiscount());
      productInfo.put("MRP", product.getMRP());
      productInfo.put("brand", product.getBrand().getBrandName());
      productsList.add(productInfo);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("productsList", productsList);
    response.put("length", bagCarts.size());

    return response;
  }

}
