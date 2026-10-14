
package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageProject;
import com.example.main.payload.VillageProjectDto;
import com.example.main.reposetry.VillageProjectRepository;
import com.example.main.service.Interface.VillageProjectService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VillageProjectServiceImpl implements VillageProjectService {

    private static final Logger log =
            LoggerFactory.getLogger(VillageProjectServiceImpl.class);

    private final VillageProjectRepository villageProjectRepository;
    private final ModelMapper mapper;

    public VillageProjectServiceImpl(
            VillageProjectRepository villageProjectRepository,
            ModelMapper mapper) {
        this.villageProjectRepository = villageProjectRepository;
        this.mapper = mapper;
    }

    @Override
    public VillageProjectDto createVillageProject(VillageProjectDto dto) {
        log.info("Creating a new village project");

        VillageProject villageProject =
                mapper.map(dto, VillageProject.class);

        VillageProject savedProject =
                villageProjectRepository.save(villageProject);

        log.info("Village project created successfully with ID {}",
                savedProject.getId());

        return mapper.map(savedProject, VillageProjectDto.class);
    }

    @Override
    public List<VillageProjectDto> getAllVillageProjects() {
        log.info("Fetching all village projects");

        List<VillageProjectDto> projects =
                villageProjectRepository.findAll()
                        .stream()
                        .map(project -> mapper.map(project, VillageProjectDto.class))
                        .toList();

        log.info("Fetched {} village projects", projects.size());

        return projects;
    }

    @Override
    public VillageProjectDto getVillageProjectById(long id) {
        log.info("Fetching village project with ID {}", id);

        VillageProject project = findProjectById(id);

        return mapper.map(project, VillageProjectDto.class);
    }

    @Override
    public VillageProjectDto updateVillageProject(
            VillageProjectDto dto, long id) {

        log.info("Updating village project with ID {}", id);

        VillageProject project = findProjectById(id);

        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            project.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            project.setDescription(dto.getDescription());
        }

        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            project.setCategory(dto.getCategory());
        }

        if (dto.getBudget() != null) {
            project.setBudget(dto.getBudget());
        }

        if (dto.getProgress() != null) {
            project.setProgress(dto.getProgress());
        }

        if (dto.getStartDate() != null) {
            project.setStartDate(dto.getStartDate());
        }

        if (dto.getExpectedEndDate() != null) {
            project.setExpectedEndDate(dto.getExpectedEndDate());
        }

        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            project.setStatus(dto.getStatus());
        }

        if (dto.getLocation() != null && !dto.getLocation().isEmpty()) {
            project.setLocation(dto.getLocation());
        }

        VillageProject savedProject =
                villageProjectRepository.save(project);

        log.info("Village project updated successfully with ID {}", id);

        return mapper.map(savedProject, VillageProjectDto.class);
    }

    @Override
    public void deleteVillageProject(long id) {
        log.info("Deleting village project with ID {}", id);

        VillageProject project = findProjectById(id);

        villageProjectRepository.delete(project);

        log.info("Village project deleted successfully with ID {}", id);
    }

    @Override
    public long getProjectCount() {
        long count = villageProjectRepository.count();

        log.debug("Total village project count: {}", count);

        return count;
    }

    private VillageProject findProjectById(long id) {
        return villageProjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound("Village project not found with ID: " + id));
    }
}
