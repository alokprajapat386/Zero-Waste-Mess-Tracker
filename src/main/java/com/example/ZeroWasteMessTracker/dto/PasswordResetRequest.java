package com.example.ZeroWasteMessTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {
    @NotBlank(message = "Token is required")
    private String token;

    @NotBlank(message = "Username/Email is required")
    private String identifier;

    @NotBlank(message = "Password is required")
    @Size(min=8, message = "Password must be atleast 8 characters long")
    private String password;
}
