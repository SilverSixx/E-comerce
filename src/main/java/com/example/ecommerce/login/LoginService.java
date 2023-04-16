package com.example.ecommerce.login;

import com.example.ecommerce.customer.Customer;
import com.example.ecommerce.customer.CustomerRepository;
import com.example.ecommerce.exception.CustomerNotEnabledException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final static String CUSTOMER_NOT_FOUND_MSG = "customer with email %s not found";
    private final CustomerRepository customerRepository;
    public Boolean login(String username, String password) {
        Optional<Customer> customerFoundByUsername = customerRepository.findByEmail(username);
        if(customerFoundByUsername.isEmpty()){
            throw new UsernameNotFoundException(String.format(CUSTOMER_NOT_FOUND_MSG, username));
        }
        if(!customerFoundByUsername.get().getEnabled()){
            throw new CustomerNotEnabledException("customer has not enabled");
        }
        if(!password.equals(customerFoundByUsername.get().getPassword())){
            throw new IllegalStateException("customer's password is incorrect");
        }
        return true;
    }
}
