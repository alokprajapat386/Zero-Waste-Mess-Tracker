package com.example.ZeroWasteMessTracker.dto;

import com.example.ZeroWasteMessTracker.enums.MealType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealSessionRequest {


    private LocalDateTime startTime=LocalDateTime.now();
        @NotNull(message = "End time is required")
    private LocalDateTime endTime;
    @NotNull(message = "Meal type is required")
    private MealType mealType;
    private String menu;
}
