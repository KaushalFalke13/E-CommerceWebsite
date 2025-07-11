package com.example.demo.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.Repositories.BagRepo;
import com.example.demo.Repositories.OrderRepo;
import com.example.demo.Repositories.WatchlistRepo;
import com.example.demo.Services.ProductsServicesImpl;
import com.example.demo.Services.UserserviceImpl;
import com.example.demo.entities.BagCart;
import com.example.demo.entities.Orders;
import com.example.demo.entities.Products;
import com.example.demo.entities.Users;
import com.example.demo.entities.WatchListCart;
import com.example.demo.forms.UsersForm;

@Controller
public class pageController {


    @Autowired
    private ProductsServicesImpl ProductsServices;

    @Autowired
    private UserserviceImpl userservice;

    @Autowired
    private WatchlistRepo watchlistRepo;

    @Autowired
    private BagRepo bagRepo;


    @Autowired
    private OrderRepo orderRepo;

    
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("UsersForm", new UsersForm());
        return "login";
    }

    @RequestMapping("/Adminlogin")
    public String Adminlogin(Model model) {
        model.addAttribute("UsersForm", new UsersForm());
        return "Adminlogin";
    }

    @RequestMapping("/Signup")
    public String Signup(Model model) {
        model.addAttribute("UsersForm", new UsersForm());
        return "Signup";
    }

    @RequestMapping("/AdminSignup")
    public String AdminSignup(Model model) {
        model.addAttribute("UsersForm", new UsersForm());
        return "AdminSignup";
    }
  

    @RequestMapping("/watchlist")
    public String watchlist(Model model, Principal principal) {
        String username = principal.getName();
        Users user = userservice.findByEmail(username);
        List<WatchListCart> cartlist = watchlistRepo.findAllByUser(user);
        List<Products> newCartList = new ArrayList<>();

        for (WatchListCart watchListCart : cartlist) {
            Products product = ProductsServices.SearchProductById(watchListCart.getProducts().getProductId());
            newCartList.add(product);
        }
        model.addAttribute("newCartList", newCartList);
        model.addAttribute("user", user);
        model.addAttribute("length", newCartList.size());
        return "watchlist";
    }

    @RequestMapping("/Bag")
    public String Bag(Model model,Principal principal) {
        String username = principal.getName();
        Users user = userservice.findByEmail(username);
        List<BagCart> BagList = bagRepo.findAllByUser(user);
        List<Products> newCartList = new ArrayList<>();

        for (BagCart watchListCart : BagList) {
            Products product = ProductsServices.SearchProductById(watchListCart.getProducts().getProductId());
            newCartList.add(product);
        }
        model.addAttribute("newCartList", newCartList);
        model.addAttribute("user", user);
        model.addAttribute("length", newCartList.size());
        return "Bag";
    }

    @RequestMapping("/Order")
    public String Order(Principal principal,Model model) {
        String username = principal.getName();
        Users user = userservice.findByEmail(username);
  
        List<Orders> orderList = orderRepo.findAllByUser(user);

        model.addAttribute("orderList", orderList);
        return "Orders";
    }

    // @SuppressWarnings("unchecked")
    // @RequestMapping(value = "/getOrderdata", method = RequestMethod.POST)
    // public String getOrderdata(@RequestParam("orderDetail")  String orderDetailJson,Principal principal ) throws JsonMappingException, JsonProcessingException { 

    // ObjectMapper objectMapper = new ObjectMapper(); 
    // Map<String, Object> orderDetail = objectMapper.readValue(orderDetailJson, Map.class);

    // String username = principal.getName();
    // map.put(username,orderDetail);
   
    // return "redirect:confirmOrder";     
    // }

    

    
}
