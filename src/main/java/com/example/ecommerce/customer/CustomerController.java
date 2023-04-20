package com.example.ecommerce.customer;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
