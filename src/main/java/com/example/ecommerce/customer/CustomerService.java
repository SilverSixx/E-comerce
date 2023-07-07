package com.example.ecommerce.customer;

import com.example.ecommerce.exception.CustomerWithIDNotFound;
import com.example.ecommerce.registration.token.ConfirmationService;
import com.example.ecommerce.registration.token.ConfirmationToken;
import com.example.ecommerce.role.Role;
import com.example.ecommerce.role.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {
    private final static String CUSTOMER_NOT_FOUND_MSG = "customer with email %s not found";
    private final CustomerRepository customerRepository;
    private final ConfirmationService confirmationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepo roleRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(CUSTOMER_NOT_FOUND_MSG, email)));
        return new User(
                customer.getEmail(),
                customer.getPassword(),
                customer.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList())
        );
    }

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    @Transactional
    public void deleteCustomer(Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            throw new CustomerWithIDNotFound("customer with id "+ id+ "not found");
        }
        confirmationService.deleteTokenByCustomerId(id);
        customerRepository.delete(customer.get());
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
        String hashedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);
        customerRepository.save(customer);
    }

    public void addRoleToCustomer(String roleName, String customerEmail){
        Customer customerFoundByEmail = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Customer does not exist"));
        Role roleFoundByName = roleRepo.findByRoleName(roleName);
        if(roleFoundByName == null){
            throw new IllegalStateException("Role does not exist");
        }
        customerFoundByEmail.getRoles().add(roleFoundByName);
    }
    public void enableCustomer(String email){
        customerRepository.enableCustomer(email);
    }
}
