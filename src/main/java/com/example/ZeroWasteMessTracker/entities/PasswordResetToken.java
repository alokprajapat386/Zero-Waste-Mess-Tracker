package com.example.ZeroWasteMessTracker.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Long id;
    @Column(unique = true)
    private String token= UUID.randomUUID().toString();

    private String username;

    private LocalDateTime expiry;

    @PrePersist
    public  void onCreate(){
        expiry=LocalDateTime.now().plusMinutes(15);
    }

}
