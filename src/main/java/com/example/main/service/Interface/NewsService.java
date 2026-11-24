package com.example.main.service.Interface;

import com.example.main.payload.NewsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NewsService {

    NewsDto createNews(NewsDto newsDto , MultipartFile multipartFile) throws Exception;

    List<NewsDto> getAllNews();

    List<NewsDto> getPublishedNews();

    NewsDto getNewsById(long id);

    NewsDto updateNews(NewsDto newsDto, long id);

    void deleteNews(long id);
}