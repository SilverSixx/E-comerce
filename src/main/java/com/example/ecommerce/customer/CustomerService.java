package com.example.ecommerce.customer;

import com.example.ecommerce.registration.token.ConfirmationService;
import com.example.ecommerce.registration.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {
    private final static String CUSTOMER_NOT_FOUND_MSG = "customer with email %s not found";
    private final CustomerRepository customerRepository;
    private ConfirmationService confirmationService;
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(CUSTOMER_NOT_FOUND_MSG, email)));
    }

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }


    // return a link for confirmation
    @Transactional
    public String signUpCustomer(Customer customer){
        boolean customerExists = customerRepository.findByEmail(customer.getEmail()).isPresent();
        if(customerExists){
            // TODO: if user hasn't confirmed, and the attributes are the same
            throw new IllegalStateException("email already taken");
        }
        customerRepository.save(customer);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken= new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            customer
        );
        confirmationService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }
    public void enableCustomer(String email){
        customerRepository.enableCustomer(email);
    }
}
