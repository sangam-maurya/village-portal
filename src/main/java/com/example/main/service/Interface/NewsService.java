package com.example.main.service.Interface;

import com.example.main.payload.NewsDto;
import java.util.List;

public interface NewsService {

    NewsDto createNews(NewsDto newsDto);

    List<NewsDto> getAllNews();

    List<NewsDto> getPublishedNews();

    NewsDto getNewsById(long id);

    NewsDto updateNews(NewsDto newsDto, long id);

    void deleteNews(long id);
}