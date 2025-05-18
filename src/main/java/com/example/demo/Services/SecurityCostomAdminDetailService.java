package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCostomAdminDetailService implements UserDetailsService {

  @Autowired
  private AdminService adminservice;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return adminservice.findAdminByEmail(username);
  }
}
