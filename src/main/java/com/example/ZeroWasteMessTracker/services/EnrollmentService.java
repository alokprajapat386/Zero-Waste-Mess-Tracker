package com.example.ZeroWasteMessTracker.services;

import com.example.ZeroWasteMessTracker.dto.EnrollmentHistoryRequest;
import com.example.ZeroWasteMessTracker.dto.UserProfileResponse;
import com.example.ZeroWasteMessTracker.dto.UserResponse;
import com.example.ZeroWasteMessTracker.entities.Enrollment;
import com.example.ZeroWasteMessTracker.entities.MealSession;
import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.enums.EnrollmentStatus;
import com.example.ZeroWasteMessTracker.exceptions.EnrollmentNotFoundException;
import com.example.ZeroWasteMessTracker.exceptions.UserNotFoundException;
import com.example.ZeroWasteMessTracker.repositories.EnrollmentRepository;
import com.example.ZeroWasteMessTracker.repositories.MealSessionRepository;
import com.example.ZeroWasteMessTracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final MealSessionRepository mealSessionRepository;
    private final UserRepository userRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, MealSessionRepository mealSessionRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.mealSessionRepository = mealSessionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void enroll(Long userId, String qrToken){
        User user=userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        MealSession session=mealSessionRepository.findByQrToken(qrToken).orElseThrow(() -> new RuntimeException("Session not found"));
        if(enrollmentRepository.existsByUserAndMealSession(user, session)){
            Enrollment enrollment = enrollmentRepository.findByUser_IdAndMealSession_Id(user.getId(), session.getId())
                    .orElseThrow(()-> new IllegalStateException("Already Enrolled"));
        }
        LocalDateTime now=LocalDateTime.now();
        if(now.isBefore(session.getStartTime()) || now.isAfter(session.getEndTime())){
            throw new IllegalStateException("Session Inactive");
        }

        Enrollment enrollment=new Enrollment();
        enrollment.setUser(user);
        enrollment.setMealSession(session);
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollment.setEnrollmentStatus(EnrollmentStatus.ENROLLED);

        enrollmentRepository.save(enrollment);

        session.setEnrollmentsCount(session.getEnrollmentsCount()+1 );
        mealSessionRepository.save(session);
    }

    public void cancel(Long userId, Long mealSessionId){
        User user=userRepository.findById(userId).orElseThrow(
                UserNotFoundException::new);
        MealSession session=mealSessionRepository.findById(mealSessionId)
                .orElseThrow(RuntimeException::new);

        Enrollment enrollment =enrollmentRepository.findByUser_IdAndMealSession_Id(user.getId(),session.getId())
                .orElseThrow(EnrollmentNotFoundException::new);

        if(enrollment.getEnrollmentStatus()==EnrollmentStatus.CANCELLED){
            throw new IllegalArgumentException("Enrollment already cancelled");
        }

        enrollment.setEnrollmentStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(enrollment);
        session.setEnrollmentsCount(session.getEnrollmentsCount()-1);
        mealSessionRepository.save(session);
    }

    public List<UserProfileResponse> getRegisteredStudents(Long sessionId){
        List<Enrollment> enrollments=
                enrollmentRepository.findByMealSession_IdAndEnrollmentStatus(sessionId, EnrollmentStatus.ENROLLED);
        return enrollments.stream()
                .map(enrollment -> {
                        User user=enrollment.getUser();
                        return new UserProfileResponse(
                               user
                        );
                })
                .toList();
    }

    public List<Enrollment> getEnrollments(String username){
        User user=userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return enrollmentRepository.findByUser_Id(user.getId());
    }
}
