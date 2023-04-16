package com.example.ecommerce.login;

import com.example.ecommerce.exception.CustomerNotEnabledException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor

public class LoginController {
    private final LoginService loginService;
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password){
        try {
            Boolean canBeLogin = loginService.login(username, password);
            return ResponseEntity.ok().body(canBeLogin);
        } catch (CustomerNotEnabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Customer has not enabled their account.");
        } catch (UsernameNotFoundException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }
}
