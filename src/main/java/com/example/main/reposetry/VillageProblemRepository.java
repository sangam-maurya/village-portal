package com.example.main.reposetry;

import com.example.main.entity.VillageProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VillageProblemRepository extends JpaRepository<VillageProblem, Long> {
    List<VillageProblem> findByReportedBy(String reportedBy);
    Optional<VillageProblem> findByIdAndReportedBy(long id, String username);
    long countByReportedBy(String username);
}