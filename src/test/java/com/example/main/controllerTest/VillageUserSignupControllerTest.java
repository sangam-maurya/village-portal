package com.example.main.controllerTest;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.service.Interface.VillageUserSignupService;
import com.example.main.service.VillageUserSignupImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.main.controller.VillageUserSignupController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class VillageUserSignupControllerTest {

     @InjectMocks
     private VillageUserSignupController villageUserSignupController;

    @Mock
    private VillageUserSignupService villageUserSignupService;




    @Test
    public void createUser(){
        VillageUserSignupDto dto = new VillageUserSignupDto();
        dto.setRole("NAME");
        dto.setPassword("1234");
        dto.setEmail("sangam");

        when(villageUserSignupService.cereateVillageUserSignup(dto))
                .thenReturn(dto);

        ResponseEntity<?> response =
                villageUserSignupController.createUser(dto);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        assertThat(response.getBody())
                .isEqualTo(dto);

    }

    @Test
    void  getUser(){
       List<VillageUserSignupDto> list = new ArrayList<>();
        when(villageUserSignupService.getVillageUserSignup())
                .thenReturn(list);

        ResponseEntity<?> response = villageUserSignupController.getAllData();

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isEqualTo(list);
    }

//    void deleteUserById(){
//     VillageUserSignupDto dto = new VillageUserSignupDto();
//        when(villageUserSignupService.deleteVillageUserSignup())
//                .thenReturn(dto)
//
//    }

}
