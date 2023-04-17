package com.example.ecommerce.registration;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 1. The client sends an HTTP POST request to the registration endpoint, which is handled by the RegistrationController.
 * 2. The RegistrationController receives the request and extracts the registration data from the request body, which is in the form of a RegistrationRequest object.
 * 3. The RegistrationController validates the registration data and delegates the registration logic to the RegistrationService. // security
 * 4. The RegistrationService creates a new AppUser object with the registration data and sets the user role to USER by default.
 * 5. The RegistrationService performs any additional business logic, such as generating a verification token and sending an email to the user for email verification.
 * 6. The RegistrationService saves the new AppUser object to the database using the AppUserRepository.
 * 7. Finally, the RegistrationService returns a response indicating the success or failure of the registration process.
 */
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    // @RequestBody : Spring automatically map the request body (in form of JSON) to the parameter object
    @PostMapping(value="/registration")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try{
            registrationService.register(request);
            return ResponseEntity.ok().body("ok");
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email already taken.");
        }
    }
    @GetMapping(value = "/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirm(token);
    }

}
