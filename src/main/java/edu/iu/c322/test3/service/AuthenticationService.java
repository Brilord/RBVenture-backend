package edu.iu.c322.test3.service;

import edu.iu.c322.test3.repository.CustomerRepository;
import edu.iu.habahram.primesservice.model.Customer;
import edu.iu.habahram.primesservice.repository.AuthenticationDBRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService implements
        IAuthenticationService , UserDetailsService {
    CustomerRepository customerRepository;


    public AuthenticationService(
            CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer register(Customer customer) throws IOException {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        customer.setPassword(passwordEncoded);
        return customerRepository.save(customer);
    }

    @Override
    public boolean login(String username, String password) throws IOException {
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null) {
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            if(bc.matches(password, customer.getPassword())) {
                return true;
            }
            return false;
        }
        return false;
    }



    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        try {
            Customer customer =
                    authenticationRepository.findByUsername(username);
            if(customer == null) {
                throw new UsernameNotFoundException("");
            }
            return User
                    .withUsername(username)
                    .password(customer.getPassword())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
