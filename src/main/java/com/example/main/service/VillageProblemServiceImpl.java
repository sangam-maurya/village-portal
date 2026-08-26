package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageProblem;
import com.example.main.payload.VillageProblemDto;
import com.example.main.reposetry.VillageProblemRepository;
import com.example.main.service.Interface.VillageProblemService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VillageProblemServiceImpl implements VillageProblemService {

    private final VillageProblemRepository villageProblemRepository;
    private final ModelMapper mapper;

    public VillageProblemServiceImpl(VillageProblemRepository villageProblemRepository,
                                     ModelMapper mapper) {
        this.villageProblemRepository = villageProblemRepository;
        this.mapper = mapper;
    }

    @Override
    public VillageProblemDto createVillageProblem(VillageProblemDto dto) {

        // DTO → Entity
        VillageProblem villageProblem =
                mapper.map(dto, VillageProblem.class);

        // Backend controlled fields
        villageProblem.setCreatedAt(LocalDateTime.now());
        villageProblem.setStatus("REPORTED");

        // Logged-in user ka username
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        villageProblem.setReportedBy(authentication.getName());

        // Database mein save
        VillageProblem saved =
                villageProblemRepository.save(villageProblem);

        // Entity → DTO
        return mapper.map(saved, VillageProblemDto.class);
    }

    @Override
    public List<VillageProblemDto> getAllVillageProblems() {

        List<VillageProblem> problems =
                villageProblemRepository.findAll();

        return problems.stream()
                .map(problem -> mapper.map(problem, VillageProblemDto.class))
                .toList();
    }

    @Override
    public VillageProblemDto getVillageProblemById(long id) {

        VillageProblem problem =
                villageProblemRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFound("Problem not found with id " + id));

        return mapper.map(problem, VillageProblemDto.class);
    }

    @Override
    public VillageProblemDto updateVillageProblem(
            VillageProblemDto dto, long id) {

        VillageProblem problem =
                villageProblemRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFound("Problem not found with id " + id));

        problem.setTitle(dto.getTitle());
        problem.setDescription(dto.getDescription());
        problem.setCategory(dto.getCategory());
        problem.setLocation(dto.getLocation());
        problem.setPriority(dto.getPriority());

        // Admin status update kar sakta hai
        if (dto.getStatus() != null) {
            problem.setStatus(dto.getStatus());
        }

        // RESOLVED hone par resolvedAt set karo
        if ("RESOLVED".equalsIgnoreCase(dto.getStatus())) {
            problem.setResolvedAt(LocalDateTime.now());
        }

        VillageProblem saved =
                villageProblemRepository.save(problem);

        return mapper.map(saved, VillageProblemDto.class);
    }

    @Override
    public void deleteVillageProblem(long id) {

        VillageProblem problem =
                villageProblemRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFound("Problem not found with id " + id));

        villageProblemRepository.delete(problem);
    }
    @Override
    public List<VillageProblemDto> getMyVillageProblems(String username) {

        List<VillageProblem> problems =
                villageProblemRepository.findByReportedBy(username);

        return problems.stream()
                .map(problem -> mapper.map(problem, VillageProblemDto.class))
                .toList();
    }
}