package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demo.Services.SecurityCostomAdminDetailService;
import com.example.demo.Services.SecurityCostomUserDetailService;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCostomUserDetailService userDetailsService;

    @Autowired
    private SecurityCostomAdminDetailService adminDetailsService ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Configure ADMIN security chain
    @Bean
    @Order(1) // Higher priority
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        // Create authentication provider for admins
        DaoAuthenticationProvider adminAuthProvider = new DaoAuthenticationProvider();
        adminAuthProvider.setUserDetailsService(adminDetailsService);
        adminAuthProvider.setPasswordEncoder(passwordEncoder);

        // Build authentication manager for admins
        AuthenticationManager adminAuthenticationManager = new ProviderManager(adminAuthProvider);

        http
        .securityMatcher("/admin/**", "/Adminlogin*") // URLs handled by this chain
        .authorizeHttpRequests(auth ->
        auth
            .requestMatchers("/admin/**").authenticated() // Only `/admin/**` URLs need specific authorization
            .anyRequest().permitAll() // All other requests are permitted (open to everyone)
        )
            .authenticationManager(adminAuthenticationManager) // Use admin auth manager
            .formLogin(form -> form
                .loginPage("/Adminlogin")
                .loginProcessingUrl("/Adminlogin") // Matches admin login form action
                .defaultSuccessUrl("/admin/Dashboard", true)
                .failureUrl("/Adminlogin?error=true")
                .permitAll()
            ) 
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/Adminlogin")
            )
            .csrf(csrf -> csrf.disable()); // Adjust CSRF as needed

        return http.build();
    }

    // Configure USER security chain
    @Bean
    @Order(2) // Lower priority
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        // Create authentication provider for users
        DaoAuthenticationProvider userAuthProvider = new DaoAuthenticationProvider();
        userAuthProvider.setUserDetailsService(userDetailsService);
        userAuthProvider.setPasswordEncoder(passwordEncoder);

        // Build authentication manager for users
        AuthenticationManager userAuthenticationManager = new ProviderManager(userAuthProvider);

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/**", "/Bag", "/addProducts", "/watchlist").authenticated()
                .anyRequest().permitAll()
            )
            .authenticationManager(userAuthenticationManager) // Use user auth manager
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login") // Matches user login form action
                .defaultSuccessUrl("/showProducts", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
            )
            .csrf(csrf -> csrf.disable()); // Adjust CSRF as needed

        return http.build();
    }
}