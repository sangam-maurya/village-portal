package com.example.main.controllerTest;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.service.Interface.VillageUserSignupService;
import com.example.main.service.VillageUserSignupImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.TokenDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.example.main.controller.VillageUserSignupController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
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

   private VillageUserSignupDto villageUserSignupDto;

   @BeforeEach
   void setUp(){
       villageUserSignupDto = new VillageUserSignupDto();
       villageUserSignupDto.setId(1L);
       villageUserSignupDto.setUsername("Sangam");
   }

    @Test
    public void createUser(){

        when(villageUserSignupService.cereateVillageUserSignup(villageUserSignupDto))
                .thenReturn(villageUserSignupDto);

        ResponseEntity<?> response =
                villageUserSignupController.createUser(villageUserSignupDto);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        assertThat(response.getBody())
                .isEqualTo("User Created Successfully");

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

    @Test
    void getUserById(){
        VillageUserSignupDto villageUserSignupDto = new VillageUserSignupDto();
        villageUserSignupDto.setId(1L);
        when(villageUserSignupService.getUserDataById(villageUserSignupDto.getId()))
                .thenReturn(villageUserSignupDto);

        ResponseEntity<?> response = villageUserSignupController.getUserDetailById(villageUserSignupDto.getId());

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .isEqualTo(villageUserSignupDto);
    }

    @Test
    void  getUserByUserName() {
     when(villageUserSignupService.findByUsername(villageUserSignupDto.getUsername()))
             .thenReturn(villageUserSignupDto);

     ResponseEntity<?> response = villageUserSignupController.findUsername(villageUserSignupDto.getUsername());
     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
     assertThat(response.getBody()).isEqualTo(villageUserSignupDto);
    }
    @Test
    void verifyLogin_Success() {

        VillageUserLoginDto loginDto = new VillageUserLoginDto();
        loginDto.setUsername("Sangam");
        loginDto.setPassword("test123");

        TokenDto tokenDto = new TokenDto();

        when(villageUserSignupService.login(loginDto))
                .thenReturn(tokenDto);

        ResponseEntity<?> response =
                villageUserSignupController.verifyLogin(loginDto);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .isEqualTo(tokenDto);

        verify(villageUserSignupService, times(1))
                .login(loginDto);
    }

    @Test
    void verifyLogin_InvalidCredentials() {

        VillageUserLoginDto loginDto = new VillageUserLoginDto();
        loginDto.setUsername("Sangam");
        loginDto.setPassword("wrongPassword");

        when(villageUserSignupService.login(loginDto))
                .thenReturn(null);

        ResponseEntity<?> response =
                villageUserSignupController.verifyLogin(loginDto);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(response.getBody())
                .isEqualTo("Invalid password or Username");

        verify(villageUserSignupService, times(1))
                .login(loginDto);
    }

}
