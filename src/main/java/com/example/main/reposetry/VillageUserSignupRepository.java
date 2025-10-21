package com.example.main.reposetry;

import com.example.main.entity.VillageUserSignup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VillageUserSignupRepository extends JpaRepository<VillageUserSignup, Long> {
    Optional<VillageUserSignup> findByUsername(String username);
    Optional<VillageUserSignup> findByEmail(String email);
}