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
        VillageProject villageProject = villageProjectRepository.findById(id).orElseThrow(() -> new ResourceNotFound("id is not present"));
        villageProject.setTitle(dto.getTitle());
        villageProject.setDescription(dto.getDescription());
        villageProject.setCategory(dto.getCategory());
        villageProject.setBudget(dto.getBudget());
        villageProject.setProgress(dto.getProgress());
        villageProject.setStartDate(dto.getStartDate());
        villageProject.setExpectedEndDate(dto.getExpectedEndDate());
        villageProject.setStatus(dto.getStatus());
        villageProject.setLocation(dto.getLocation());
        VillageProject save = villageProjectRepository.save(villageProject);
        VillageProjectDto map = mapper.map(save, VillageProjectDto.class);
        return  map;
    }

    @Override
    public void deleteVillageProject(long id) {
        VillageProject villageProject = villageProjectRepository.findById(id).orElseThrow(() -> new ResourceNotFound("id is not present"));
      villageProjectRepository.delete(villageProject);
    }
}
