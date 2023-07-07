package com.example.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ECommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner commandLineRunner(CustomerService customerService, RoleService roleService){
//        return args -> {
//            Role customerRole = roleService.saveRole(new Role(null, "CUSTOMER"));
//
//            Customer customer = new Customer(null, "abc@gmail.com", "123456", new ArrayList<>(), true);
//            customer.getRoles().add(customerRole);
//            customerService.saveCustomer(customer);
//            customerService.addRoleToCustomer("CUSTOMER", "abc@gmail.com");
//        };
//    }
}
