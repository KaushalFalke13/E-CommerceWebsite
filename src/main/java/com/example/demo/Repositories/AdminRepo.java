package com.example.demo.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Admin;


public interface AdminRepo extends JpaRepository<Admin,Integer>{

  Admin findByEmail(String email);  

  @SuppressWarnings({ "null", "unchecked" })
  Admin save(Admin admin);

}

