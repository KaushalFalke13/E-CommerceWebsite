package com.example.demo.controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.Services.BagServiceImpl;

@Controller
public class BagRequestController {

  @Autowired
  private BagServiceImpl bagService;

  @RequestMapping(value = "/addToBag", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> addProductsToBag(Principal principal, @RequestParam("productId") String productId) {
    return bagService.addProductToBag(principal,productId);
  }

  @RequestMapping(value = "/removeBaglistItem", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> removeBaglistItem(Principal principal, @RequestParam("productId") String productId) {
    return bagService.removeProductFromBag(principal,productId);
  }


  @RequestMapping("/getBaglist")
  @ResponseBody
  public Map<String, Object> getBaglist(Principal principal) {
    if (principal == null) {
      return Collections.emptyMap();
    }
    return bagService.getBagCartList(principal);
  }

}




 