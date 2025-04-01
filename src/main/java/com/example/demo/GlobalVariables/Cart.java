package com.example.demo.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class Cart {

    List<Integer> cartlist = new ArrayList<Integer>();  

    public List<Integer> getCartlist() {
      return cartlist;
    }
    public void addData(Integer cartListItem) {
      if(!cartlist.contains(cartListItem)){
        cartlist.add(cartListItem);
      }
    }

    public void removeData(Integer cartListItem) {
      if(cartlist.contains(cartListItem)){
        cartlist.remove(cartListItem);
      }
    }

    @Override
    public String toString() {
      return "Cart [cartlist=" + cartlist + "]";
    }

    public void setCartlist(List<Integer> cartlist) {
      this.cartlist = cartlist;
    }
}
