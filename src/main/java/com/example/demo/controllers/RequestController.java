package com.example.demo.controllers;

import java.security.Principal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Repositories.*;
import com.example.demo.Services.ProductsServicesImpl;
import com.example.demo.Services.UserserviceImpl;
import com.example.demo.entities.*;
import com.example.demo.forms.*;

@Controller
public class RequestController {

    @Autowired
    private UserserviceImpl userservice;

    @Autowired
    private ProductsServicesImpl ProductsServices;

    @Autowired
    private WatchlistRepo watchlistRepo;

    @RequestMapping(value = "/ProcessSignupData", method = RequestMethod.POST)
    public String ProcessSignupData(@ModelAttribute UsersForm usersForm) {
        String userID = UUID.randomUUID().toString();
        Users user = Users.builder()
                .userid(userID)
                .name(usersForm.getName())
                .email(usersForm.getEmail())
                .password(usersForm.getPassword())
                .build();
        userservice.saveUser(user);
        return "redirect:/showProducts";
    }


    
    @RequestMapping(value = "/addtoWatchlist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addtoWatchlist(Principal principal, @RequestParam("productId") String productId) {
        String username = principal.getName();
        Users user = userservice.findByEmail(username);
        Products product = ProductsServices.SearchProductById(productId);
        Optional<WatchListCart> isWatchListCartPresent = watchlistRepo.findByUserAndProducts(user, product);
        if (isWatchListCartPresent.isPresent()) {
            System.out.println("Already product added");
        } else {
            WatchListCart cart = WatchListCart.builder()
                    .user(user)
                    .products(product)
                    .build();
            watchlistRepo.save(cart);
        }
        Map<String, Object> response = new HashMap<>();
        List<WatchListCart> watchlist = watchlistRepo.findAllByUser(user);
        response.put("length", watchlist.size());
        return response;
    }

    @RequestMapping(value = "/removeWatchlistItem", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeWatchlistItem(Principal principal, @RequestParam("productId") String productId) {
        Map<String, Object> response = new HashMap<>();
        String username = principal.getName();
        Users user = userservice.findByEmail(username);
        Products removedProduct = ProductsServices.SearchProductById(productId);
        Optional<WatchListCart> watchListCart = watchlistRepo.findByUserAndProducts(user, removedProduct);

        watchlistRepo.delete(watchListCart.get());
        List<Map<String, Object>> productsList = new ArrayList<>();
        List<WatchListCart> cartlist = watchlistRepo.findAllByUser(user);
        for (WatchListCart cart : cartlist) {
            Map<String, Object> productInfo = new HashMap<>();
            Products product = cart.getProducts();
            productInfo.put("productId", product.getProductId());
            productInfo.put("title", product.getTitle());
            productInfo.put("price", product.getPrice());
            productInfo.put("imagepath", product.getImages().getImage1());
            productInfo.put("discount", product.getDiscount());
            productInfo.put("MRP", product.getMRP());
            productsList.add(productInfo);
        }
        response.put("length", cartlist.size());
        response.put("productsList", productsList);
        response.put("removedProduct", removedProduct.getImages().getImage1());
        return response;
    }

    @RequestMapping("/getCartlist")
    @ResponseBody
    public Map<String, Object> getCart(Principal principal) {
        if (principal == null) {
            return Collections.emptyMap();
        }
        String username = principal.getName();
        Users user = userservice.findByEmail(username);
        List<WatchListCart> cartlist = watchlistRepo.findAllByUser(user);
        Map<String, Object> response = new HashMap<>();
        response.put("length", cartlist.size());
        return response;
    }

}