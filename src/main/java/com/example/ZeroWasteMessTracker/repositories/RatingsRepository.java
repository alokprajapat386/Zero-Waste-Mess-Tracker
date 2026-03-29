package com.example.ZeroWasteMessTracker.repositories;


import com.example.ZeroWasteMessTracker.entities.Ratings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    boolean existsByMealSession_IdAndUser_Id(Long mealSessionId, Long User_Id);

    Optional<Ratings> findByUser_IdAndMealSession_Id(Long userId, Long sessionId);

    List<Ratings> findByMealSession_Id(Long sessionId);

    List<Ratings> findByUser_Id(Long userId);

    List<Ratings> findByUser_IdOrderByCreatedAtDesc(Long userId);

}
