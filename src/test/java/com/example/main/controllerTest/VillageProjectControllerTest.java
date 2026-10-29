package com.example.main.controllerTest;
import com.example.main.controller.VillageProjectServiceController;
import com.example.main.payload.VillageProjectDto;
import com.example.main.service.Interface.VillageProjectService;

import com.example.main.service.VillageProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class VillageProjectControllerTest {

    @Mock
    private VillageProjectService villageProjectServicel;

    @InjectMocks
    private VillageProjectServiceController villageProjectServiceController;

    private VillageProjectDto projectDto;

    @BeforeEach
    void setUp() {
        projectDto = new VillageProjectDto();
        projectDto.setId(1L);
        projectDto.setBudget(100L);
        projectDto.setCategory("ROAD");
        projectDto.setLocation("Near Mallupur");
    }

    @Test
    public void createProject() {

        when(villageProjectServicel.createVillageProject(projectDto))
                .thenReturn(projectDto);

        ResponseEntity<?> response =
                villageProjectServiceController.createAboutVillage(projectDto);

        assertThat(response.getBody()).isEqualTo(projectDto);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        verify(villageProjectServicel, times(1))
                .createVillageProject(projectDto);
    }

    @Test
    public  void getAboutVillage(){
        List<VillageProjectDto> list = new ArrayList<>();
        when(villageProjectServicel.getAllVillageProjects()).thenReturn(list);

        ResponseEntity<List<VillageProjectDto>> response = villageProjectServiceController.getAboutVillage();

        assertThat(response.getBody()).isEqualTo(list);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(villageProjectServicel, times(1))
                .getAllVillageProjects();
    }
    @Test
    public  void getAboutVillageByID(){
        when(villageProjectServicel.getVillageProjectById(projectDto.getId())).thenReturn(projectDto);

        ResponseEntity<?> response = villageProjectServiceController.getAboutVillageById(projectDto.getId());

        assertThat(response.getBody()).isEqualTo(projectDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
