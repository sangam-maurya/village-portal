package com.example.main.service.Interface;
import com.example.main.payload.GovernmentServiceDto;

import java.util.List;

public interface GovernmentService {

    GovernmentServiceDto create(GovernmentServiceDto dto);

    GovernmentServiceDto update(Long id, GovernmentServiceDto dto);

    void delete(Long id);

    List<GovernmentServiceDto> getPublishedServices();

    List<GovernmentServiceDto> getPublishedByCategory(String category);

    GovernmentServiceDto getPublishedById(Long id);
}
