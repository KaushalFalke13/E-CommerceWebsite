package com.example.demo.Services;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.example.demo.forms.AddressForms;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface OrdersService {

  public String startOrderPayments(Principal principal);

  public ResponseEntity<String> confirmOrders(Principal principal);

  public void getOrderData(Principal principal,String orderDetail) throws JsonMappingException, JsonProcessingException;

  public ResponseEntity<AddressForms> saveNewAddress(Principal principal,AddressForms addressForm);
}
