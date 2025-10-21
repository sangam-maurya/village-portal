package com.example.main.reposetry;

import com.example.main.entity.VillageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VillageInfoRepository extends JpaRepository<VillageInfo, Long> {
}