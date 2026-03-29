package com.example.ZeroWasteMessTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRatingsRequest {
    private String username;
    private int page;
    private int size;

}
