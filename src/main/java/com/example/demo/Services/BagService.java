package com.example.demo.Services;

import java.security.Principal;
import java.util.Map;


public interface BagService {

  public Map<String, Object> addProductToBag(Principal principal,String productId);

  public Map<String, Object> removeProductFromBag(Principal principal,String productId);

  public Map<String, Object> getBagCartList(Principal principal);
  
}
