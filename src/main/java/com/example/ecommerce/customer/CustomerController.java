package com.example.ecommerce.customer;

import com.example.ecommerce.role.Role;
import com.example.ecommerce.role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final RoleService roleService;
    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }
    @PostMapping("/role/save")
    public ResponseEntity<?> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/role/save").toUriString());
        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
