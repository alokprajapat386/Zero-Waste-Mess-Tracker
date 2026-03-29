package com.example.ZeroWasteMessTracker.entities;

import com.example.ZeroWasteMessTracker.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "approval_requests",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"hostel_id", "user_id"})
        },
        indexes = {
                @Index(name="idx_hostel", columnList = "hostel_id" ),
                @Index(name="idx_hostel_user", columnList = "hostel_id, user_id")
        }
)
@Data
@NoArgsConstructor
public class ApprovalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostel_id", nullable = false)
    private Hostel hostel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus=ApprovalStatus.PENDING;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    @PrePersist
    protected void onCreate(){
        createdAt= LocalDateTime.now();
        updatedAt= LocalDateTime.now();
        approvalStatus=ApprovalStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt=LocalDateTime.now();
    }

}
