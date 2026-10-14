
package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageProblem;
import com.example.main.payload.VillageProblemDto;
import com.example.main.reposetry.VillageProblemRepository;
import com.example.main.service.Interface.VillageProblemService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VillageProblemServiceImpl implements VillageProblemService {

    private static final Logger log =
            LoggerFactory.getLogger(VillageProblemServiceImpl.class);

    private final VillageProblemRepository villageProblemRepository;
    private final ModelMapper mapper;

    public VillageProblemServiceImpl(
            VillageProblemRepository villageProblemRepository,
            ModelMapper mapper) {
        this.villageProblemRepository = villageProblemRepository;
        this.mapper = mapper;
    }

    @Override
    public VillageProblemDto createVillageProblem(VillageProblemDto dto) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authenticated user is required");
        }

        VillageProblem problem = mapper.map(dto, VillageProblem.class);
        problem.setCreatedAt(LocalDateTime.now());
        problem.setStatus("PENDING");
        problem.setReportedBy(authentication.getName());

        VillageProblem saved = villageProblemRepository.save(problem);

        log.info("Village problem created successfully with ID {}",
                saved.getId());

        return mapper.map(saved, VillageProblemDto.class);
    }

    @Override
    public List<VillageProblemDto> getAllVillageProblems() {
        List<VillageProblemDto> problems =
                villageProblemRepository.findAll()
                        .stream()
                        .map(problem -> mapper.map(problem, VillageProblemDto.class))
                        .toList();

        log.info("Fetched {} village problems", problems.size());

        return problems;
    }

    @Override
    public VillageProblemDto getVillageProblemById(long id, String username) {
        log.info("Fetching village problem with ID {}", id);

        VillageProblem problem =
                villageProblemRepository.findByIdAndReportedBy(id, username)
                        .orElseThrow(() ->
                                new ResourceNotFound(
                                        "Problem not found with id " + id));

        return mapper.map(problem, VillageProblemDto.class);
    }

    @Override
    public VillageProblemDto updateVillageProblem(
            VillageProblemDto dto, long id) {
        log.info("Updating village problem with ID {}", id);

        VillageProblem problem =
                villageProblemRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFound(
                                        "Problem not found with id " + id));

        problem.setTitle(dto.getTitle());
        problem.setDescription(dto.getDescription());
        problem.setCategory(dto.getCategory());
        problem.setLocation(dto.getLocation());
        problem.setPriority(dto.getPriority());

        if (dto.getStatus() != null) {
            problem.setStatus(dto.getStatus());
        }

        if ("RESOLVED".equalsIgnoreCase(dto.getStatus())) {
            problem.setResolvedAt(LocalDateTime.now());
        }

        VillageProblem saved = villageProblemRepository.save(problem);

        log.info("Village problem updated successfully with ID {}",
                saved.getId());

        return mapper.map(saved, VillageProblemDto.class);
    }

    @Override
    public void deleteVillageProblem(long id) {
        VillageProblem problem =
                villageProblemRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFound(
                                        "Problem not found with id " + id));

        villageProblemRepository.delete(problem);

        log.info("Village problem deleted successfully with ID {}", id);
    }

    @Override
    public List<VillageProblemDto> getMyVillageProblems(String username) {
        List<VillageProblemDto> problems =
                villageProblemRepository.findByReportedBy(username)
                        .stream()
                        .map(problem -> mapper.map(problem, VillageProblemDto.class))
                        .toList();

        log.info("Fetched {} village problems for the current user",
                problems.size());

        return problems;
    }

    @Override
    public long getTotalProblems() {
        return villageProblemRepository.count();
    }

    @Override
    public long getPendingProblems(String msg) {
        return villageProblemRepository.countByStatus("PENDING");
    }

    @Override
    public long getResolvedProblems(String msg) {
        return villageProblemRepository.countByStatus("RESOLVED");
    }
}
