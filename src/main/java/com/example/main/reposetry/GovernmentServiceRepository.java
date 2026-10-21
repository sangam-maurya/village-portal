package com.example.main.reposetry;

import com.example.main.entity.GovernmentServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GovernmentServiceRepository
        extends JpaRepository<GovernmentServiceEntity, Long> {

    List<GovernmentServiceEntity>
    findByPublishedTrueOrderByTitleAsc();

    List<GovernmentServiceEntity>
    findByPublishedTrueAndCategoryIgnoreCaseOrderByTitleAsc(String category);

    boolean existsByTitleIgnoreCaseAndCategoryIgnoreCase(
            String title, String category);
}
