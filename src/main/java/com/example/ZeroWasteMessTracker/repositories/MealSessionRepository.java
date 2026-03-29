package com.example.ZeroWasteMessTracker.repositories;

import com.example.ZeroWasteMessTracker.entities.MealSession;
import com.example.ZeroWasteMessTracker.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MealSessionRepository extends JpaRepository<MealSession, Long> {
    List<MealSession> findByHostel_Id(Long hostelId);

    List<MealSession> findByHostel_IdAndActive(Long hostelId, boolean active);

    Optional<MealSession> findByQrToken(String qrToken);

    boolean existsByQrToken(String qrToken);

    List<MealSession> findByHostel_IdAndMealType(Long hostelId, MealType mealType);

    List<MealSession> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

}
