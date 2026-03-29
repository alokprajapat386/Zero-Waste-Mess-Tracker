package com.example.ZeroWasteMessTracker.controllers;

import com.example.ZeroWasteMessTracker.dto.MealSessionRequest;
import com.example.ZeroWasteMessTracker.dto.MealSessionResponse;
import com.example.ZeroWasteMessTracker.dto.UserProfileResponse;
import com.example.ZeroWasteMessTracker.dto.UserResponse;
import com.example.ZeroWasteMessTracker.entities.MealSession;
import com.example.ZeroWasteMessTracker.services.EnrollmentService;
import com.example.ZeroWasteMessTracker.services.MealSessionService;
import com.example.ZeroWasteMessTracker.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal-sessions")
public class MealSessionController  {


    private final MealSessionService mealSessionService;
    private final EnrollmentService enrollmentService;
    private final UserService userService;

    public MealSessionController(MealSessionService mealSessionService, EnrollmentService enrollmentService, UserService userService) {
        this.mealSessionService = mealSessionService;
        this.enrollmentService = enrollmentService;
        this.userService = userService;
    }
    @PreAuthorize("hasRole('MESS_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<MealSessionResponse> createSession(
            @Valid @RequestBody MealSessionRequest request,
            Authentication auth
            ){
        MealSession session=mealSessionService.createSession(
                request,
                userService.getUserByUsername(auth.getName()).getHostel().getUniqueHostelId()
        );

        return ResponseEntity.ok(
               new MealSessionResponse(session)
        );
    }

    @PreAuthorize("hasRole('MESS_MANAGER')")
    @GetMapping("/{id}/headcount")
    public int headcount(@PathVariable Long id){
        return mealSessionService.getHeadcount(id);
    }



    @PreAuthorize("hasRole('MESS_MANAGER')")
    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserProfileResponse>> students(@PathVariable Long id){

        return ResponseEntity.ok(
                enrollmentService.getRegisteredStudents(id)
        );
    }

    @GetMapping("/all")
    ResponseEntity<List<MealSessionResponse>> getAllSessions(Authentication auth){
        return ResponseEntity.ok(
                mealSessionService.getAllSessions(userService.getUserByUsername(auth.getName()).getHostel().getId())
        );
    }

    @GetMapping("/all-active")
    ResponseEntity<List<MealSessionResponse>> getAllActiveSession(Authentication auth){
        return ResponseEntity.ok(
                mealSessionService.getActiveSessions(userService.getUserByUsername(auth.getName()).getHostel().getId())
        );
    }
}
