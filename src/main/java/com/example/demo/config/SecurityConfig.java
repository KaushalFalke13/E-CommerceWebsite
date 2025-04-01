package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.Services.SecurityCostomUserDetailService;


@Configuration
public class SecurityConfig {

  @Autowired
  private SecurityCostomUserDetailService userDetailService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Bean 
  public DaoAuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

    return  daoAuthenticationProvider;
  }
  @SuppressWarnings("removal")
  @Bean 
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
    
    httpSecurity.authorizeHttpRequests(authorize->{
      // authorize.requestMatchers("/showProducts","Signup").permitAll();
      authorize.requestMatchers("/Bag","/addProducts","/watchlist").authenticated();
      authorize.requestMatchers("/admin/**").hasRole("ADMIN");  // Requires ROLE_ADMIN

      try {
        httpSecurity.csrf().disable();
      } catch (Exception e) {
        e.printStackTrace();
      }

      authorize.anyRequest().permitAll();
    });

    // httpSecurity.formLogin(formlogin->{
    //   formlogin.loginPage("/Login")
    //   .successForwardUrl("/showProducts") 
    //   .failureForwardUrl("/index");
    // });
    httpSecurity.formLogin()
            .loginPage("/login") // Custom login page URL
            .loginProcessingUrl("/login") // URL to process login
            .defaultSuccessUrl("/showProducts", true) // Redirect to this page upon successful login
            .failureUrl("/login?error=true") // Redirect to login page on failure with an error
            .permitAll();
    return httpSecurity.build();
  }
}

