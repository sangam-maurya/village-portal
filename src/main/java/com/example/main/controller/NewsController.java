package com.example.main.controller;

import com.example.main.payload.NewsDto;
import com.example.main.service.Interface.NewsService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;
    private final ObjectMapper objectMapper;
    public NewsController(NewsService newsService, ObjectMapper objectMapper) {
        this.newsService = newsService;
        this.objectMapper = objectMapper;
    }

//    @PostMapping("/admin/news")
//    public ResponseEntity<NewsDto> createNews(
//            @Valid @RequestBody NewsDto newsDto) {
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(newsService.createNews(newsDto));
//    }

    @PostMapping(value = "/admin/news", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewsDto> createNews(
            @RequestPart("newsDto") String newsDtoJson,
            @RequestPart(value = "newsImage", required = false) MultipartFile newsImage)
            throws Exception {

        NewsDto newsDto = objectMapper.readValue(newsDtoJson, NewsDto.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsService.createNews(newsDto, newsImage));
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
