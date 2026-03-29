package com.example.ZeroWasteMessTracker.controllers;

import com.example.ZeroWasteMessTracker.dto.EnrollmentCancelRequest;
import com.example.ZeroWasteMessTracker.dto.EnrollmentHistoryRequest;
import com.example.ZeroWasteMessTracker.dto.EnrollmentRequest;
import com.example.ZeroWasteMessTracker.entities.Enrollment;
import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.services.EnrollmentService;
import com.example.ZeroWasteMessTracker.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

        private final EnrollmentService enrollmentService;
        private final UserService userService;
    public EnrollmentController(EnrollmentService enrollmentService, UserService userService) {
        this.enrollmentService = enrollmentService;
        this.userService=userService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(
            Authentication auth,
            @RequestBody EnrollmentRequest request
            ){

        User user=userService.getUserByUsername(auth.getName());
        enrollmentService.enroll(user.getId(), request.getQrToken());

        return ResponseEntity.ok(
                Map.of("message", "Enrollment Successful")
        );
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cancel")
    public ResponseEntity<?> cancel(
            Authentication auth,
            @RequestBody EnrollmentCancelRequest request
    ){

        enrollmentService.cancel(
                userService.getUserByUsername(auth.getName()).getId(),
                request.getMealSessionId()
        );

        return ResponseEntity.ok(
                Map.of("message", "Enrollment Cancelled Successfully")
        );
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/meal-history")
    public ResponseEntity<List<Enrollment>> getEnrollments(
            Authentication auth
    ){

        return ResponseEntity.ok(
                enrollmentService.getEnrollments(auth.getName())
        );
    }

}
