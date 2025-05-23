package com.example.demo.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.Repositories.AddressRepo;
import com.example.demo.Services.OrdersServiceImpl;
import com.example.demo.Services.UserserviceImpl;
import com.example.demo.entities.Address;
import com.example.demo.entities.Users;
import com.example.demo.forms.AddressForms;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.razorpay.RazorpayException;

@Controller
public class OrdersController {

  @Autowired
  private UserserviceImpl userservice;
 
  @Autowired
  private AddressRepo addressRepo;

  @Autowired
  OrdersServiceImpl ordersServiceImpl;


  @GetMapping("/confirmOrder")
  public String confirmOrder(Model model, Principal principal) {
    String username = principal.getName();
    Users user = userservice.findByEmail(username);
    Address address = addressRepo.findByUser(user);
    model.addAttribute("address", address);
    return "confirmOrder";
  }

  @RequestMapping(value = "/getOrderdata", method = RequestMethod.POST)
  public String getOrderdata(Principal principal, @RequestParam("orderDetail") String orderDetailJson) throws Exception{
    ordersServiceImpl.getOrderData(principal, orderDetailJson);
    return "redirect:confirmOrder";
  }


  @RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<String> confirmOrder( Principal principal)
  throws JsonMappingException, JsonProcessingException {
    return ordersServiceImpl.confirmOrders(principal);
  }


  @RequestMapping(value = "/startPayment", method = RequestMethod.POST)
  @ResponseBody
  public String startOrderPayment(Principal principal) throws RazorpayException {
  return ordersServiceImpl.startOrderPayments(principal);
  }


  @RequestMapping(value = "/getUserAddress", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<AddressForms> saveAddress(Principal principal, @RequestBody AddressForms addressForm) {
    return ordersServiceImpl.saveNewAddress(principal, addressForm);
  }

}