package com.example.ZeroWasteMessTracker.repositories;

import com.example.ZeroWasteMessTracker.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEnrollmentId(String enrollmentId);
    Optional<User> findByUsername(String name);
    Optional<User> findByUsernameOrEmail(String username, String email);
    boolean existsByEmail(String email);
    boolean existsByEnrollmentId(String enrollmentId);
    boolean existsByUsername(String username);

    List<User> findByHostel_Id(Long hostelId);
    List<User> findByHostel_IdAndActive(Long hostelId, boolean active);

//    boolean existsByUsernameAndHostel_UniqueHostelId( String username, String uniqueHostelId);
}
