package com.example.ecommerce.login;

import com.example.ecommerce.customer.Customer;
import com.example.ecommerce.customer.CustomerRepository;
import com.example.ecommerce.exception.CustomerNotEnabledException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class LoginService {
    private final static String CUSTOMER_NOT_FOUND_MSG = "customer with email %s not found";
    private final CustomerRepository customerRepository;
    public ResponseEntity<String> login(String username, String password) {
        if (!Objects.equals(username, "admin")) {
            Customer customer = customerRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format(CUSTOMER_NOT_FOUND_MSG, username)));
            if (!customer.getEnabled()) {
                throw new CustomerNotEnabledException("customer has not enabled");
            }
            if (!Objects.equals(password, customer.getPassword())) {
                throw new IllegalStateException("customer's password is incorrect");
            }
            return ResponseEntity.ok("home_page.html");
        } else {
            if (Objects.equals(password, "admin")) {
                return ResponseEntity.ok("admin.html");
            }
            throw new IllegalArgumentException("admin's password is incorrect");
        }
    }

}
