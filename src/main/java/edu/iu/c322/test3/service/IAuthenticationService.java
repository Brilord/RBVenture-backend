package edu.iu.c322.test3.service;


import edu.iu.c322.test3.model.Customer;

import java.io.IOException;

public interface IAuthenticationService {
    Customer register(Customer customer) throws Exception;
    boolean login(String username, String password) throws IOException;
}
