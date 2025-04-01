package com.example.demo.Services;

import com.example.demo.entities.Users;

public interface UserService {

    Users findByEmail(String email);

    Users saveUser(Users user);
}
