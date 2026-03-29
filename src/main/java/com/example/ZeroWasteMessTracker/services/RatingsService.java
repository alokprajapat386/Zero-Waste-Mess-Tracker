package com.example.ZeroWasteMessTracker.services;

import com.example.ZeroWasteMessTracker.dto.RatingsAddRequest;
import com.example.ZeroWasteMessTracker.dto.RatingsResponse;
import com.example.ZeroWasteMessTracker.entities.MealSession;
import com.example.ZeroWasteMessTracker.entities.Ratings;
import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.exceptions.RatingsNotFoundException;
import com.example.ZeroWasteMessTracker.exceptions.SessionNotFoundException;
import com.example.ZeroWasteMessTracker.exceptions.UsernameAlreadyExistsException;
import com.example.ZeroWasteMessTracker.repositories.MealSessionRepository;
import com.example.ZeroWasteMessTracker.repositories.RatingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingsService {

    private final RatingsRepository ratingsRepository;
    private final UserService userService;
    private final MealSessionService mealSessionService;
    private final MealSessionRepository mealSessionRepository;

    public RatingsService(RatingsRepository ratingsRepository, UserService userService, MealSessionService mealSessionService, MealSessionRepository mealSessionRepository) {
        this.ratingsRepository = ratingsRepository;
        this.userService = userService;
        this.mealSessionService = mealSessionService;
        this.mealSessionRepository = mealSessionRepository;
    }

    public List<RatingsResponse> getRatingsForUser(String username) {
    User user=userService.getUserByUsername(username);
        return ratingsRepository.findByUser_IdOrderByCreatedAtDesc(user.getId()).stream()
                .map(RatingsResponse::new).toList();
    }

    public List<RatingsResponse> getRatingsForMeal(long mealSessionId){
        return ratingsRepository.findByMealSession_Id(mealSessionId).stream()
                .map(RatingsResponse::new).toList();
    }
    @Transactional
    public boolean addRatings(RatingsAddRequest request){

        if(request.getRating()>5){
            throw new IllegalArgumentException("Invalid ratings");
        }

        User user=userService.getUserByUsername(request.getUsername());
        MealSession mealSession = mealSessionRepository.findById(request.getMealSessionId())
                .orElseThrow(SessionNotFoundException::new);

        if(ratingsRepository.existsByMealSession_IdAndUser_Id(request.getMealSessionId(), user.getId())){
            throw new IllegalStateException("Ratings Already Exists");
        }


        mealSession.setTotalRatings(mealSession.getTotalRatings()+ request.getRating());
        mealSession.setRatingsCount(mealSession.getRatingsCount()+1);
        Ratings ratings= new Ratings();
        ratings.setUser(user);
        ratings.setMealSession(mealSession);
         ratings.setRating(request.getRating());
        ratings.setReview(request.getReview());
        ratingsRepository.save(ratings);
        mealSessionRepository.save(mealSession);
        return true;
    }
    @Transactional
    public void updateRatings(RatingsAddRequest request){
        User user = userService.getUserByUsername(request.getUsername());
        MealSession mealSession = mealSessionRepository.findById(request.getMealSessionId()).
                orElseThrow(SessionNotFoundException::new);

        Ratings ratings=ratingsRepository.findByUser_IdAndMealSession_Id(user.getId(), request.getMealSessionId())
                .orElseThrow(RatingsNotFoundException::new);
        mealSession.setTotalRatings(mealSession.getTotalRatings()-ratings.getRating()+ request.getRating());
        mealSessionRepository.save(mealSession);
        ratings.setRating(request.getRating());
        ratings.setReview(request.getReview());
        ratingsRepository.save(ratings);

    }

}
