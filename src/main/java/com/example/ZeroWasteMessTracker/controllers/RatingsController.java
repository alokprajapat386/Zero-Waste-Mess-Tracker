package com.example.ZeroWasteMessTracker.controllers;


import com.example.ZeroWasteMessTracker.dto.RatingsAddRequest;
import com.example.ZeroWasteMessTracker.dto.RatingsResponse;
import com.example.ZeroWasteMessTracker.services.RatingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class RatingsController {

    private final RatingsService ratingsService;

    public RatingsController(RatingsService ratingsService) {
        this.ratingsService = ratingsService;
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<?> addRatings(
            @RequestBody RatingsAddRequest request,
            Authentication auth
    ){
        if(!auth.getName().equals(request.getUsername())){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            Map.of("message", "Username and request are different")
                    );
        }
        boolean done=ratingsService.addRatings(request);
        if(done){
            return ResponseEntity.ok(
                    Map.of("message", "Ratings added successfully")
            );
        }else{
            return ResponseEntity.badRequest(
            ).build();
        }
    }

    @GetMapping("/meals/{mealId}")
    public ResponseEntity<List<RatingsResponse>> getRatings(
            @PathVariable Long mealId
    ){
        return ResponseEntity.ok(
                ratingsService.getRatingsForMeal(mealId)
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<RatingsResponse>> getMyRatings(
            Authentication auth
    ){
        return ResponseEntity.ok(
                ratingsService.getRatingsForUser(auth.getName())
        );
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRatings(
            @RequestBody RatingsAddRequest request,
            Authentication auth
    ){
        request.setUsername(auth.getName());
        ratingsService.updateRatings(request);
        return ResponseEntity.ok(
                Map.of("message", "Ratings updated successfully")
        );
    }
}
