package com.example.ZeroWasteMessTracker.repositories;

import com.example.ZeroWasteMessTracker.entities.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostelRepository extends JpaRepository<Hostel, Long> {

    boolean existsByUniqueHostelId( String uniqueHostelI);

    Optional<Hostel> findByUniqueHostelId(String uniqueHostelId);
}
