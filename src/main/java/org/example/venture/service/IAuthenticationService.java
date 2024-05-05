package org.example.venture.service;


import org.example.venture.model.Customer;

import java.io.IOException;

public interface IAuthenticationService {
    Customer register(Customer customer) throws Exception;
    boolean login(String username, String password) throws IOException;
}
