package com.example.ZeroWasteMessTracker.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table( name = "ratings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "meal_session_id"})
        },
        indexes = {
                @Index(name="idx_meal_session", columnList = "meal_session_id" )
        }
)
@Data
public class Ratings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int rating;

    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_session_id", nullable = false)
    private MealSession mealSession;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
