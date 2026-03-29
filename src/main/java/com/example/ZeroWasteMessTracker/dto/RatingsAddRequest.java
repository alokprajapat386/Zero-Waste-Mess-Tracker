package com.example.ZeroWasteMessTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingsAddRequest {
    private String username;
    private long mealSessionId;
    private int rating;
    private String review;
}
