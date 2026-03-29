package com.example.ZeroWasteMessTracker.controllers;

import com.example.ZeroWasteMessTracker.dto.ApprovalResponse;
import com.example.ZeroWasteMessTracker.dto.UserProfileResponse;
import com.example.ZeroWasteMessTracker.entities.ApprovalRequest;
import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.enums.ApprovalStatus;
import com.example.ZeroWasteMessTracker.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private  final UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/{id}/approve")
    public ResponseEntity<?> approveUser(@PathVariable Long id) {
        userService.approve(userService.getUserById(id).getUsername());
        return ResponseEntity.ok(
                Map.of("message", "User approved")
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/{id}/reject")
    public ResponseEntity<?> rejectUser(@PathVariable Long id) {
        userService.reject(userService.getUserById(id).getUsername());
        return ResponseEntity.ok(
                Map.of("message", "User rejected")
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/approval-requests")
    public ResponseEntity<List<ApprovalResponse>> getRequests(Authentication auth) {
        User user= userService.getUserByUsername(auth.getName());
        return ResponseEntity.ok(
                userService.getRequestSortedByDesc(user.getHostel().getId(), ApprovalStatus.PENDING)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-users")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers(Authentication auth) {
        User user = userService.getUserByUsername(auth.getName());
        return ResponseEntity.ok(
               userService.getAllUsers(user.getHostel().getId())
        );
    }


}
