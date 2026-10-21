package com.example.main.service;

import com.example.main.entity.GovernmentServiceEntity;
import com.example.main.payload.GovernmentServiceDto;
import com.example.main.reposetry.GovernmentServiceRepository;
import com.example.main.service.Interface.GovernmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GovernmentServiceImpl implements GovernmentService {

    private final GovernmentServiceRepository repository;
    private final ModelMapper modelMapper;

    public GovernmentServiceImpl(
            GovernmentServiceRepository repository,
            ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GovernmentServiceDto create(GovernmentServiceDto dto) {
        validate(dto);

        GovernmentServiceEntity entity =
                modelMapper.map(dto, GovernmentServiceEntity.class);

        setDefaultLocation(entity);

        GovernmentServiceEntity saved = repository.save(entity);

        return modelMapper.map(saved, GovernmentServiceDto.class);
    }

    @Override
    public GovernmentServiceDto update(Long id, GovernmentServiceDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        GovernmentServiceEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Government service not found: " + id));

        // Title: nayi value aayi to update, warna purani value rahegi
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            entity.setTitle(dto.getTitle());
        }

        // Category
        if (dto.getCategory() != null && !dto.getCategory().isBlank()) {
            entity.setCategory(dto.getCategory());
        }

        // Description
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }

        // Eligibility
        if (dto.getEligibility() != null) {
            entity.setEligibility(dto.getEligibility());
        }

        // Required documents
        if (dto.getRequiredDocuments() != null) {
            entity.setRequiredDocuments(dto.getRequiredDocuments());
        }

        // Application steps
        if (dto.getApplicationSteps() != null) {
            entity.setApplicationSteps(dto.getApplicationSteps());
        }

        // Official URL
        if (dto.getOfficialUrl() != null) {
            entity.setOfficialUrl(dto.getOfficialUrl());
        }

        // Location fixed rahegi
        setDefaultLocation(entity);

        GovernmentServiceEntity updated = repository.save(entity);

        return modelMapper.map(updated, GovernmentServiceDto.class);
    }


    @Override
    public void delete(Long id) {
        GovernmentServiceEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Government service not found: " + id));

        repository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GovernmentServiceDto> getPublishedServices() {
        return repository.findByPublishedTrueOrderByTitleAsc()
                .stream()
                .map(entity ->
                        modelMapper.map(entity, GovernmentServiceDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GovernmentServiceDto> getPublishedByCategory(String category) {
        return repository
                .findByPublishedTrueAndCategoryIgnoreCaseOrderByTitleAsc(category)
                .stream()
                .map(entity ->
                        modelMapper.map(entity, GovernmentServiceDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GovernmentServiceDto getPublishedById(Long id) {
        GovernmentServiceEntity entity = repository.findById(id)
                .filter(GovernmentServiceEntity::isPublished)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Published service not found: " + id));

        return modelMapper.map(entity, GovernmentServiceDto.class);
    }

    private void setDefaultLocation(GovernmentServiceEntity entity) {
        entity.setState("Uttar Pradesh");
        entity.setDistrict("Jaunpur");
        entity.setTehsil("Badlapur");
    }

    private void validate(GovernmentServiceDto dto) {
        if (dto == null
                || dto.getTitle() == null || dto.getTitle().isBlank()
                || dto.getCategory() == null || dto.getCategory().isBlank()
                || dto.getDescription() == null
                || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException(
                    "Title, category and description are required");
        }

        if (dto.getTitle().trim().length() > 150) {
            throw new IllegalArgumentException(
                    "Title must not exceed 150 characters");
        }

        String url = dto.getOfficialUrl();

        if (url != null && !url.isBlank()
                && !url.matches("^https?://.+")) {
            throw new IllegalArgumentException(
                    "Official URL must start with http:// or https://");
        }
    }
}
