package com.example.demo.Services;

import com.example.demo.entities.Admin;

public interface AdminService {
  
  Admin findAdminByEmail(String email);

  Admin saveAdmin(Admin admin);
}
