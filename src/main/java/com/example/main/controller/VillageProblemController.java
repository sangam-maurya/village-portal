package com.example.main.controller;

import com.example.main.payload.VillageProblemDto;
import com.example.main.service.VillageProblemServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/problems")
@CrossOrigin(origins = "http://localhost:3000")
public class VillageProblemController {

    private final VillageProblemServiceImpl villageProblemService;

    public VillageProblemController(VillageProblemServiceImpl villageProblemService) {
        this.villageProblemService = villageProblemService;
    }

    // Create Problem
    @PostMapping("/create")
    public ResponseEntity<VillageProblemDto> createProblem(
            @Valid @RequestBody VillageProblemDto dto) {

        VillageProblemDto problem =
                villageProblemService.createVillageProblem(dto);

        return new ResponseEntity<>(problem, HttpStatus.CREATED);
    }

    // Get All Problems
    @GetMapping
    public ResponseEntity<List<VillageProblemDto>> getAllProblems() {

        List<VillageProblemDto> problems =
                villageProblemService.getAllVillageProblems();

        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

    // Get Problem By ID
    @GetMapping("/{id}")
    public ResponseEntity<VillageProblemDto> getProblemById(
            @PathVariable long id) {

        VillageProblemDto problem =
                villageProblemService.getVillageProblemById(id);

        return new ResponseEntity<>(problem, HttpStatus.OK);
    }

    // Update Problem
    @PutMapping("/update/{id}")
    public ResponseEntity<VillageProblemDto> updateProblem(
            @PathVariable long id,
            @Valid @RequestBody VillageProblemDto dto) {

        VillageProblemDto problem =
                villageProblemService.updateVillageProblem(dto, id);

        return new ResponseEntity<>(problem, HttpStatus.OK);
    }

    // Delete Problem
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProblem(
            @PathVariable long id) {

        villageProblemService.deleteVillageProblem(id);

        return new ResponseEntity<>("Problem deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/my")
    public ResponseEntity<List<VillageProblemDto>> getMyProblems(
            Authentication authentication) {

        String username = authentication.getName();

        List<VillageProblemDto> problems =
                villageProblemService.getMyVillageProblems(username);

        return new ResponseEntity<>(problems, HttpStatus.OK);
    }
}