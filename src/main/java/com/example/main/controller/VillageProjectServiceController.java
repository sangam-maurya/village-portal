package com.example.main.controller;

import com.example.main.payload.VillageProjectDto;
import com.example.main.service.VillageProjectServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/village")
@CrossOrigin(origins = "http://localhost:3000")
public class VillageProjectServiceController {

    private final VillageProjectServiceImpl villageProjectService;

    public VillageProjectServiceController(VillageProjectServiceImpl villageProjectService) {
        this.villageProjectService = villageProjectService;
    }

    @PostMapping("/create")
    public ResponseEntity<VillageProjectDto> createAboutVillage(@Valid @RequestBody VillageProjectDto dto){
        VillageProjectDto villageProject = villageProjectService.createVillageProject(dto);
        return  new ResponseEntity<>(villageProject , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VillageProjectDto>> getAboutVillage(){
        List<VillageProjectDto> allVillageProjects = villageProjectService.getAllVillageProjects();
        return new ResponseEntity<>(allVillageProjects , HttpStatus.OK);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<VillageProjectDto> getAboutVillageById(@PathVariable long id){
        VillageProjectDto villageProjectById = villageProjectService.getVillageProjectById(id);
        return new ResponseEntity<>(villageProjectById , HttpStatus.OK);
    }
    @PutMapping("/update/byid/{id}")
    public ResponseEntity<VillageProjectDto> updateAboutVillage( @PathVariable long id ,@Valid @RequestBody VillageProjectDto dto){
        VillageProjectDto villageProjectDto = villageProjectService.updateVillageProject(dto, id);
        return new ResponseEntity<>(villageProjectDto , HttpStatus.OK);
    }
    @DeleteMapping("/delete/byid/{id}")
    public ResponseEntity<String> deleteAboutVillage(@PathVariable long id){
        villageProjectService.deleteVillageProject(id);
        return new ResponseEntity<>("item deleted" , HttpStatus.OK);
    }
}
