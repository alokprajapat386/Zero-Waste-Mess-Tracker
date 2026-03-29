package com.example.ZeroWasteMessTracker.repositories;

import com.example.ZeroWasteMessTracker.entities.ApprovalRequest;
import com.example.ZeroWasteMessTracker.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApprovalRepository extends JpaRepository<ApprovalRequest, Long> {

    Optional<ApprovalRequest> findByUser_Id(Long userId);

    List<ApprovalRequest> findByApprovalStatus(ApprovalStatus status);

    List<ApprovalRequest> findByUser_Hostel_Id(Long hostelId);

    List<ApprovalRequest> findByUser_Hostel_IdAndApprovalStatus(Long hostelId, ApprovalStatus status);

    List<ApprovalRequest> findByUser_Hostel_IdAndApprovalStatusOrderByCreatedAtAsc(Long hostelId, ApprovalStatus status);

    List<ApprovalRequest> findByUser_Hostel_IdAndApprovalStatusOrderByCreatedAtDesc(Long hostelId, ApprovalStatus status);
}
