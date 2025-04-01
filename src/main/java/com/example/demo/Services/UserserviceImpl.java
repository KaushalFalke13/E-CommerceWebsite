package com.example.demo.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Repositories.UserRepo;
import com.example.demo.entities.Users;


@Service
public class UserserviceImpl implements UserService{
  
  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override  
  public Users saveUser(Users user){
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepo.save(user);
  }

  @Override
  public Users findByEmail(String email) {
    return userRepo.findByEmail(email);
  }

}

