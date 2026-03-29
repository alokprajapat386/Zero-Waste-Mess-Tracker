package com.example.ZeroWasteMessTracker.controllers;

import com.example.ZeroWasteMessTracker.dto.*;
import com.example.ZeroWasteMessTracker.services.AuthService;
import com.example.ZeroWasteMessTracker.services.HostelService;
import com.example.ZeroWasteMessTracker.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final HostelService hostelService;
    public AuthController(AuthService authService, UserService userService, HostelService hostelService) {
        this.authService = authService;
        this.hostelService = hostelService;
        this.userService=userService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
//        System.out.println("Register request!");
        authService.register(request);
        return ResponseEntity.ok(
                Map.of("message", "User registered successfully")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/generate-reset-token")
    public ResponseEntity<?> generateResetToken(@Valid @RequestBody PasswordResetTokenRequest request){
        authService.generateToken(request);
        return ResponseEntity.ok(
                Map.of("message", "Password Reset Token Generated successfully")
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetRequest request){
        authService.resetPassword(request);
        return ResponseEntity.ok(
                Map.of("message", "Password reset successfully")
        );
    }

    @GetMapping("/username-available")
    public ResponseEntity<Map<String, Boolean>> usernameAvailable(@RequestParam String username){
//        System.out.println("Username Availability Request.");
        boolean available= userService.usernameAvailable(username);
        return ResponseEntity.ok(
                Map.of("available", available)
        );
    }

    @PostMapping("/register-hostel")
    public ResponseEntity<?> registerHostel(@Valid @RequestBody HostelAddRequest request){
        hostelService.addHostel(request);
        return ResponseEntity.ok(
                Map.of("message", "Hostel registered successfully")
        );
    }


}
