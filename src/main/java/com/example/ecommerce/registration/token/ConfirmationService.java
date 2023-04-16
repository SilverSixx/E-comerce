package com.example.ecommerce.registration.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
@Setter
public class ConfirmationService {
    // save the confirmation token
    private final ConfirmationRepository confirmationRepository;
    public void saveConfirmationToken(ConfirmationToken token){
        confirmationRepository.save(token);
    }
    public Optional<ConfirmationToken> getToken(String token){
        return confirmationRepository.findByToken(token);
    }
    public void setConfirmedAt(String token) {
        confirmationRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
