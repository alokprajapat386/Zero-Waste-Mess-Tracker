package com.example.ZeroWasteMessTracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequest {
    @NotBlank(message = "QR token cannot be empty")
    private String qrToken;
}
