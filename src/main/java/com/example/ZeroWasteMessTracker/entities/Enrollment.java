package com.example.ZeroWasteMessTracker.entities;

import com.example.ZeroWasteMessTracker.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "enrollment",
        indexes = {
                @Index(name = "idx_user", columnList = "user_id"),
                @Index(name = "idx_meal_session", columnList = "meal_session_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames ={"user_id", "meal_session_id"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="meal_session_id", nullable = false)
    private MealSession mealSession;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus enrollmentStatus=EnrollmentStatus.ENROLLED;

    private LocalDateTime enrolledAt;

    @PrePersist
    public void onCreate(){
        enrolledAt= LocalDateTime.now();
    }
}
