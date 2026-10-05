package com.example.main.controller;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.TokenDto;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.service.Interface.VillageUserSignupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class VillageUserSignupController {

    private final VillageUserSignupService userSignupService;

    public VillageUserSignupController(VillageUserSignupService userSignupService) {
        this.userSignupService = userSignupService;
    }
//
//        @PostMapping("/create")
//        public ResponseEntity<?> createUser(@Valid @RequestBody VillageUserSignupDto villageUserSignupDto){
//            VillageUserSignupDto villageUserSignupDto1 = userSignupService.cereateVillageUserSignup(villageUserSignupDto);
//            return new ResponseEntity<>(villageUserSignupDto1, HttpStatus.CREATED);
//        }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createUser(
            @RequestPart("user") String userJson,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        VillageUserSignupDto villageUserSignupDto =
                objectMapper.readValue(userJson, VillageUserSignupDto.class);

        VillageUserSignupDto result =
                userSignupService.cereateVillageUserSignup(
                        villageUserSignupDto,
                        profileImage
                );

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<VillageUserSignupDto>> getAllData() {
        List<VillageUserSignupDto> villageUserSignup = userSignupService.getVillageUserSignup();
        return new ResponseEntity<>(villageUserSignup, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        userSignupService.deleteVillageUserSignup(id);
        return new ResponseEntity<>("id is deleted " + id, HttpStatus.OK);
    }

    @GetMapping("/by-username")
    public ResponseEntity<VillageUserSignup> findUsername(@RequestParam("username") String username) {
        VillageUserSignup byUsername = userSignupService.findByUsername(username);
        return new ResponseEntity<>(byUsername, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody VillageUserLoginDto dto) {
        TokenDto tokenDto = userSignupService.login(dto);
        if (tokenDto != null) {
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid password or Username ", HttpStatus.BAD_REQUEST);
        }
    }

    // session frontend  gay delete krega usko feature banana hai
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(
                Collections.singletonMap("message", "Logout successful")
        );
    }
    @GetMapping("/get-id/{id}")
    public ResponseEntity<VillageUserSignupDto> getUserDetailById(@PathVariable long id){
        VillageUserSignupDto userDataById = userSignupService.getUserDataById(id);
        return new ResponseEntity<>(userDataById , HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getUserCount(){
        long count = userSignupService.getCount();
        return new ResponseEntity<>( "total user count is " + count , HttpStatus.OK);
    }
}
