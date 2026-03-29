package com.example.ZeroWasteMessTracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollmentHistoryRequest {

    @NotBlank(message = "Username is required")
    private String username;

    private int page;
    private int size;
}
