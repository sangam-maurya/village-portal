package com.example.main.service.Interface;

import com.example.main.entity.VillageProject;
import com.example.main.payload.VillageProjectDto;

import java.util.List;

public interface VillageProjectService {

    VillageProjectDto createVillageProject(VillageProjectDto dto);

    List<VillageProjectDto> getAllVillageProjects();

    VillageProjectDto getVillageProjectById(long id);

    VillageProjectDto updateVillageProject(VillageProjectDto dto, long id);

    void deleteVillageProject(long id);
}
