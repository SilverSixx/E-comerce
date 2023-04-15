package com.example.ecommerce.email;

public interface EmailSender { // for switch implementations
    void send(String to, String email);
}
