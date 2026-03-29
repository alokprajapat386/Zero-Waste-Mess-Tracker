package com.example.ZeroWasteMessTracker.dto;

import com.example.ZeroWasteMessTracker.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message="Username is required")
    @Size(min=3, max=32)
    private String username;

    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min=8, message="Password must be atleast 8 characters long")
    private String password;

    @NotBlank(message = "Enrollment ID is required")
    private String enrollmentId;

    private Role role=Role.USER;

    private String uniqueHostelId;

}
