package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageProject;
import com.example.main.payload.VillageProjectDto;
import com.example.main.reposetry.VillageProjectRepository;
import com.example.main.service.Interface.VillageProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class VillageProjectServiceImpl implements VillageProjectService {

    private final VillageProjectRepository villageProjectRepository;
    private final ModelMapper mapper;


    public VillageProjectServiceImpl(VillageProjectRepository villageProjectRepository, ModelMapper mapper) {
        this.villageProjectRepository = villageProjectRepository;
        this.mapper = mapper;
    }

    @Override
    public VillageProjectDto createVillageProject(VillageProjectDto dto) {
        VillageProject villageProject = mapper.map(dto, VillageProject.class);
        VillageProject save = villageProjectRepository.save(villageProject);
        VillageProjectDto map = mapper.map(save, VillageProjectDto.class);
        return map;
    }

    @Override
    public List<VillageProjectDto> getAllVillageProjects() {
        List<VillageProject> all = villageProjectRepository.findAll();
        List<VillageProjectDto> collect = all.stream().map(a -> mapper.map(a, VillageProjectDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public VillageProjectDto getVillageProjectById(long id) {
        VillageProject villageProject = villageProjectRepository.findById(id).orElseThrow(() -> new ResourceNotFound("id is not present"));
        VillageProjectDto map = mapper.map(villageProject, VillageProjectDto.class);
        return map;
    }

    @Override
    public VillageProjectDto updateVillageProject(VillageProjectDto dto, long id) {

        VillageProject villageProject = villageProjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("id is not present"));

        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            villageProject.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            villageProject.setDescription(dto.getDescription());
        }

        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            villageProject.setCategory(dto.getCategory());
        }

        if (dto.getBudget() != null) {
            villageProject.setBudget(dto.getBudget());
        }

        if (dto.getProgress() != null) {
            villageProject.setProgress(dto.getProgress());
        }

        if (dto.getStartDate() != null) {
            villageProject.setStartDate(dto.getStartDate());
        }

        if (dto.getExpectedEndDate() != null) {
            villageProject.setExpectedEndDate(dto.getExpectedEndDate());
        }

        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            villageProject.setStatus(dto.getStatus());
        }

        if (dto.getLocation() != null && !dto.getLocation().isEmpty()) {
            villageProject.setLocation(dto.getLocation());
        }

        VillageProject save = villageProjectRepository.save(villageProject);

        return mapper.map(save, VillageProjectDto.class);
    }

    @Override
    public void deleteVillageProject(long id) {
        VillageProject villageProject = villageProjectRepository.findById(id).orElseThrow(() -> new ResourceNotFound("id is not present"));
      villageProjectRepository.delete(villageProject);
    }

    @Override
    public long getProjectCount(){
        long count = villageProjectRepository.count();
        return count;
    }
}
