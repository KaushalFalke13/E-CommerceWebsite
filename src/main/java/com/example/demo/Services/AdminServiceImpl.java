package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.Repositories.AdminRepo;
import com.example.demo.entities.Admin;

@Service
public class AdminServiceImpl implements AdminService{
 
@Autowired
  private AdminRepo adminrepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override  
  public Admin saveAdmin(Admin admin){
      admin.setPassword(passwordEncoder.encode(admin.getPassword()));
    return adminrepo.save(admin);
  }

  @Override
  public Admin findAdminByEmail(String email) {
    return adminrepo.findByEmail(email);
  }
}
