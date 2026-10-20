package com.example.main.reposetry;

import com.example.main.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByActiveTrueOrderByPublishedAtDesc();
}