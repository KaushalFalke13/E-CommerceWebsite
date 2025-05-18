package com.example.demo.ServicesTest;

import com.example.demo.Repositories.AdminRepo;
import com.example.demo.Services.AdminServiceImpl;
import com.example.demo.entities.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    AdminRepo adminRepo;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AdminServiceImpl adminServiceImpl;

    Admin admin = new Admin();

    @BeforeEach
    void beforeAllTest(){
       admin.setAdminId("123");
       admin.setName("Kaushal");
       admin.setEmail("kaushal@gmail.com");
    }

    @Test
    void  saveAdminTest(){
        Mockito.when(adminRepo.save(admin)).thenReturn(admin);
        Admin admin1 = adminServiceImpl.saveAdmin(admin);
        Assertions.assertEquals(admin1.getAdminId(),"123");
        Assertions.assertEquals(admin1.getName(),"Kaushal");
    }

    @Test
    void findAdminByEmailTest(){
        Mockito.when(adminRepo.findByEmail("kaushal1@gmail.com")).thenReturn(admin);
        Admin admin1 = adminServiceImpl.findAdminByEmail("kaushal1@gmail.com");
        Assertions.assertEquals(admin1.getAdminId(),"123");
        Assertions.assertEquals(admin1.getName(),"Kaushal");
        Assertions.assertEquals(admin1.getEmail(),"kaushal@gmail.com");

    }
}
