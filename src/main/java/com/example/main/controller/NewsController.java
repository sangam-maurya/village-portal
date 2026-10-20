package com.example.main.controller;

import com.example.main.payload.NewsDto;
import com.example.main.service.Interface.NewsService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/admin/news")
    public ResponseEntity<NewsDto> createNews(
            @Valid @RequestBody NewsDto newsDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsService.createNews(newsDto));
    }

    @GetMapping("/admin/news")
    public ResponseEntity<List<NewsDto>> getAllNews() {

        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("/admin/news/{id}")
    public ResponseEntity<NewsDto> getNewsById(
            @PathVariable long id) {

        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @PutMapping("/admin/news/{id}")
    public ResponseEntity<NewsDto> updateNews(
            @PathVariable long id,
            @RequestBody NewsDto newsDto) {

        return ResponseEntity.ok(
                newsService.updateNews(newsDto, id));
    }

    @DeleteMapping("/admin/news/{id}")
    public ResponseEntity<Void> deleteNews(
            @PathVariable long id) {

        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getPublicNews() {

        return ResponseEntity.ok(newsService.getPublishedNews());
    }
}
