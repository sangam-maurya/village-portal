package com.example.main.service.Interface;

import com.example.main.payload.VillageProblemDto;

import java.util.List;

public interface VillageProblemService {

    VillageProblemDto createVillageProblem(VillageProblemDto dto);

    List<VillageProblemDto> getAllVillageProblems();

    VillageProblemDto getVillageProblemById(long id);

    VillageProblemDto updateVillageProblem(VillageProblemDto dto, long id);

    void deleteVillageProblem(long id);

    List<VillageProblemDto> getMyVillageProblems(String username);
}