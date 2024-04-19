package edu.iu.c322.test3.service;

import edu.iu.c322.test3.model.Customer;
import edu.iu.c322.test3.repository.CustomerRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

//
//public class AuthenticationService implements
////        IAuthenticationService , UserDetailsService
////        {
//    CustomerRepository customerRepository;
//
//
//    public AuthenticationService(
//            CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
//
////    @Override
////    public Customer register(Customer customer) throws Exception {
////        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
////        String passwordEncoded = bc.encode(customer.getPassword());
////        customer.setPassword(passwordEncoded);
////        return null;
////    }
//
//    @Override
//    public boolean login(String username, String password) throws IOException {
//        Customer customer = customerRepository.findByUsername(username);
//        if (customer != null) {
//            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//            if(bc.matches(password, customer.password())) {
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }
//
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username)
//            throws UsernameNotFoundException {
//        try {
//            Customer customer =
//                    customerRepository.findByUsername(username);
//            if(customer == null) {
//                throw new UsernameNotFoundException("");
//            }
//            return User
//                    .withUsername(username)
//                    .password(customer.password())
//                    .build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
