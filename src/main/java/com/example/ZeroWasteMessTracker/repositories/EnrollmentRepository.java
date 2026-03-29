package com.example.ZeroWasteMessTracker.repositories;

import com.example.ZeroWasteMessTracker.entities.Enrollment;
import com.example.ZeroWasteMessTracker.entities.MealSession;
import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.enums.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByUserAndMealSession(User user, MealSession mealSession);

    Optional<Enrollment> findByUser_IdAndMealSession_Id(Long userId, Long sessionId);

    List<Enrollment> findByMealSession_Id(Long sessionId);

    List<Enrollment> findByUser_Id(Long userId);

    List<Enrollment> findByMealSession_IdAndEnrollmentStatus(Long sessionId, EnrollmentStatus status);

}
