package org.example.venture.service;


import org.example.venture.model.User;

import java.io.IOException;

public interface IAuthenticationService {
    User register(User customer) throws Exception;
    boolean login(String username, String password) throws IOException;
}
