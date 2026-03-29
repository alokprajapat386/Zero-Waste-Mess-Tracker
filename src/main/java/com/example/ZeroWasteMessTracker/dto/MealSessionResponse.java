package com.example.ZeroWasteMessTracker.dto;


import com.example.ZeroWasteMessTracker.entities.MealSession;
import com.example.ZeroWasteMessTracker.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealSessionResponse {
    private long id;
    private MealType mealType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int enrollmentsCount;
    private String qrToken;
    private String menu;
    private double totalRatings;
    private int ratingsCount;


    public MealSessionResponse(MealSession session){
        this.id= session.getId();
        this.mealType=session.getMealType();
        this.startTime=session.getStartTime();
        this.endTime=session.getEndTime();
        this.enrollmentsCount=session.getEnrollmentsCount();
        this.qrToken=session.getQrToken();
        this.menu=session.getMenu();
        this.totalRatings=session.getTotalRatings();
        this.ratingsCount=session.getRatingsCount();
    }
}
