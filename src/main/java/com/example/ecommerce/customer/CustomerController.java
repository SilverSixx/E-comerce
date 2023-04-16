package com.example.ecommerce.customer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/users")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping
    public List<Customer> getUsers(){
        return customerService.getCustomers();
    }

}
