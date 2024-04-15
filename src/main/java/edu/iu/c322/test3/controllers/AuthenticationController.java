package edu.iu.c322.test3.controllers;

import edu.iu.habahram.primesservice.model.Customer;
import edu.iu.habahram.primesservice.service.IAuthenticationService;
import edu.iu.habahram.primesservice.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final IAuthenticationService authenticationService;

    private TokenService tokenService;


    public AuthenticationController(AuthenticationManager authenticationManager,
                                    IAuthenticationService authenticationService,
                                    TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }


    @PostMapping("/signup")
    public boolean register(@RequestBody Customer customer) {
        try {
            authenticationService.register(customer);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signin")
    public String login(@RequestBody Customer customer) {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    customer.getUsername()
                                    , customer.getPassword()));

                return authentication.createToken();
    }


}
