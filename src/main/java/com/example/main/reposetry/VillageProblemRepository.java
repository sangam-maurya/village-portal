package com.example.main.reposetry;

import com.example.main.entity.VillageProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VillageProblemRepository extends JpaRepository<VillageProblem, Long> {
    List<VillageProblem> findByReportedBy(String reportedBy);
}