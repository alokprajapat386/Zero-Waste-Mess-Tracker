package com.example.ZeroWasteMessTracker.controllers;


import com.example.ZeroWasteMessTracker.dto.ChangePasswordRequest;
import com.example.ZeroWasteMessTracker.dto.UpdateUserRequest;
import com.example.ZeroWasteMessTracker.dto.UserProfileResponse;
import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication auth){
//        System.out.println("Something : " +auth.getName());
        User user= userService.getUserByUsername(auth.getName());
        return ResponseEntity.ok(
                new UserProfileResponse(user)
        );
    }



    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            Authentication auth,
            @RequestBody UpdateUserRequest request
            ){
        User user=userService.getUserByUsername(auth.getName());

        User updated=userService.updateProfile(user.getId(),request);
        return ResponseEntity.ok(
                new UserProfileResponse(updated)
        );
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            Authentication auth,
            @Valid @RequestBody ChangePasswordRequest request
            ){
        userService.changePassword(auth.getName(), request);

        return ResponseEntity.ok(
                Map.of("message", "Password changed successfully")
        );
    }


}
