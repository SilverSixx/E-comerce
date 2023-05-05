package com.example.ecommerce;

import com.example.ecommerce.customer.Customer;
import com.example.ecommerce.customer.CustomerRole;
import com.example.ecommerce.customer.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
}
