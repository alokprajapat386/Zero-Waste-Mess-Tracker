package com.example.ZeroWasteMessTracker.dto;

import com.example.ZeroWasteMessTracker.entities.ApprovalRequest;
import com.example.ZeroWasteMessTracker.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalResponse {
    private long  id;
    private String name;
    private String username;
    private String email;
    private long userId;
    private ApprovalStatus approvalStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public ApprovalResponse(ApprovalRequest request){
        this.id=request.getId();
        this.username=request.getUser().getUsername();
        this.userId=request.getUser().getId();
        this.approvalStatus=request.getApprovalStatus();
        this.createdAt=request.getCreatedAt();
        this.updatedAt=request.getUpdatedAt();
        this.name=request.getUser().getName();
        this.email=request.getUser().getEmail();
    }
}
