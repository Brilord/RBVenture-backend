package org.example.venture.controllers;

import org.example.venture.model.User;
import org.example.venture.repository.CustomerRepository;
import org.example.venture.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    CustomerRepository customerRepository;

    private final TokenService tokenService;


    public AuthenticationController(AuthenticationManager authenticationManager,
                                    TokenService tokenService,
                                    CustomerRepository customerRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.customerRepository = customerRepository;
    }


    @PostMapping("/signup")
    public boolean register(@RequestBody User customer) {
        try {
            System.out.println("hello there");
            customerRepository.save(customer);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signin")
    public String login(@RequestBody User customer) {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    customer.getUsername()
                                    ,customer.getPassword()));

                return tokenService.generateToken(authentication);
    }

    @PostMapping("{id}")
    public String changePassword(@RequestBody User customer) {
        try {
            System.out.println("hello there");
            customerRepository.updatePassword(customer);
            return "Password changed successfully";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping()
    public String deleteAccount(@RequestBody User customer) {
        try {
            System.out.println("hello there");
            customerRepository.deleteAccount(customer);
            return "Account deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
