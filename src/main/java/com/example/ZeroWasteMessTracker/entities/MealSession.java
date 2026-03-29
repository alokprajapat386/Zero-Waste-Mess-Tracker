package com.example.ZeroWasteMessTracker.entities;

import com.example.ZeroWasteMessTracker.enums.MealType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "meal_sessions",
        indexes = {
                @Index(name = "idx_hostel", columnList = "hostel_id"),
                @Index(name = "idx_hostel_active", columnList = "hostel_id, active")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(unique = true, nullable = false, updatable = false)
    private String qrToken;

    @Column(nullable = false)
    private boolean active=true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @Column(nullable = false)
    private int enrollmentsCount=0;

    @Column(length = 500)
    private String menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    @Version
    private  Long version;

    private double totalRatings=0;
    private int ratingsCount=0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    public void onCreate(){

        if (endTime.isBefore(startTime) || endTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt=LocalDateTime.now();
    }

}
