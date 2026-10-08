package com.example.main.service.Interface;

import com.example.main.payload.VillageProblemDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VillageProblemService {

    VillageProblemDto createVillageProblem(VillageProblemDto dto);

    List<VillageProblemDto> getAllVillageProblems();

    VillageProblemDto getVillageProblemById(long id, String username);

    VillageProblemDto updateVillageProblem(VillageProblemDto dto, long id);

    void deleteVillageProblem(long id);

    List<VillageProblemDto> getMyVillageProblems(String username);
    long getTotalProblems();

    long getPendingProblems(String msg);

    long getResolvedProblems(String msg);
}