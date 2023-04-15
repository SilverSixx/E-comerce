package com.example.ecommerce.registration.token;

import com.example.ecommerce.customer.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "_confirmation_token")
public class ConfirmationToken {
    @SequenceGenerator(
            name = "token_sequence",
            sequenceName = "token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence"
    )
    private Long id;
    @Column(name="_token")
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne   // 1 user can have many tokens
    @JoinColumn(
            nullable = false,
            name = "customer_id"
    )
    private Customer customer;
    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt,  Customer customer) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiredAt;
        this.customer = customer;
    }
}
