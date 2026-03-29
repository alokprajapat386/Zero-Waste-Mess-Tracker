package com.example.ZeroWasteMessTracker.dto;

import com.example.ZeroWasteMessTracker.entities.Ratings;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RatingsResponse {

    private String name;
    private String username;
    private LocalDateTime createdAt;
    private int rating;
    private String review;
    private long id;

    public RatingsResponse(Ratings ratings){
        this.name=ratings.getUser().getName();
        this.username=ratings.getUser().getUsername();
        this.createdAt=ratings.getCreatedAt();
        this.rating=ratings.getRating();
        this.review=ratings.getReview();
        this.id=ratings.getId();
    }


}
