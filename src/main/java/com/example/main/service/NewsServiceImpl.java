package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.News;
import com.example.main.payload.NewsDto;
import com.example.main.reposetry.NewsRepository;
import com.example.main.service.Interface.NewsService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    private final ModelMapper mapper;

    public NewsServiceImpl(
            NewsRepository repository,
            ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public NewsDto createNews(NewsDto newsDto) {

        News news = mapper.map(newsDto, News.class);

        news.setPublishedAt(LocalDateTime.now());
        news.setActive(true);

        News savedNews = repository.save(news);

        return mapper.map(savedNews, NewsDto.class);
    }

    // Admin: active and inactive news
    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getAllNews() {

        return repository.findAll()
                .stream()
                .map(news -> mapper.map(news, NewsDto.class))
                .toList();
    }

    // Public: only active news
    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getPublishedNews() {

        return repository.findByActiveTrueOrderByPublishedAtDesc()
                .stream()
                .map(news -> mapper.map(news, NewsDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDto getNewsById(long id) {

        News news = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "News not found with id: " + id));

        return mapper.map(news, NewsDto.class);
    }

    @Override
    public NewsDto updateNews(NewsDto newsDto, long id) {

        News news = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "News not found with id: " + id));

        if (newsDto.getTitle() != null
                && !newsDto.getTitle().isBlank()) {
            news.setTitle(newsDto.getTitle().trim());
        }

        if (newsDto.getDescription() != null
                && !newsDto.getDescription().isBlank()) {
            news.setDescription(newsDto.getDescription());
        }

        if (newsDto.getCategory() != null
                && !newsDto.getCategory().isBlank()) {
            news.setCategory(newsDto.getCategory().trim());
        }

        if (newsDto.getImageUrl() != null) {
            news.setImageUrl(newsDto.getImageUrl());
        }
        if (newsDto.getSourceUrl() != null) {
            news.setSourceUrl(newsDto.getSourceUrl());
        }

        // sourceUrl is handled after adding it to the entity.

        News updatedNews = repository.save(news);

        return mapper.map(updatedNews, NewsDto.class);
    }

    @Override
    public void deleteNews(long id) {

        News news = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "News not found with id: " + id));

        repository.delete(news);
    }
}