package com.example.main.controller;

import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.TokenDto;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.service.Interface.VillageUserSignupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class VillageUserSignupController {

    private static final Logger log =
            LoggerFactory.getLogger(VillageUserSignupController.class);

    private final VillageUserSignupService userSignupService;

    public VillageUserSignupController(
            VillageUserSignupService userSignupService) {
        this.userSignupService = userSignupService;
    }

    /*
    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody VillageUserSignupDto villageUserSignupDto) {

        VillageUserSignupDto villageUserSignupDto1 =
                userSignupService.cereateVillageUserSignup(
                        villageUserSignupDto);

        return new ResponseEntity<>(
                villageUserSignupDto1,
                HttpStatus.CREATED);
    }
    */

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createUser(
            @RequestPart("user") String userJson,
            @RequestParam(
                    value = "profileImage",
                    required = false) MultipartFile profileImage)
            throws Exception {

        log.info("User signup request received");

        ObjectMapper objectMapper = new ObjectMapper();

        VillageUserSignupDto villageUserSignupDto =
                objectMapper.readValue(
                        userJson,
                        VillageUserSignupDto.class);

        VillageUserSignupDto result =
                userSignupService.cereateVillageUserSignup(
                        villageUserSignupDto,
                        profileImage);

        log.info("User signup completed successfully");

        return new ResponseEntity<>(
                result,
                HttpStatus.CREATED);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<VillageUserSignupDto>> getAllData() {

        log.info("Fetching all users");

        List<VillageUserSignupDto> villageUserSignup =
                userSignupService.getVillageUserSignup();

        return new ResponseEntity<>(
                villageUserSignup,
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(
            @PathVariable Long id) {

        log.info("Delete user request received for id: {}", id);

        userSignupService.deleteVillageUserSignup(id);

        log.info("User deleted successfully with id: {}", id);

        return new ResponseEntity<>(
                "id is deleted " + id,
                HttpStatus.OK);
    }

    @GetMapping("/by-username")
    public ResponseEntity<VillageUserSignup> findUsername(
            @RequestParam("username") String username) {

        log.info("Fetching user by username: {}", username);

        VillageUserSignup byUsername =
                userSignupService.findByUsername(username);

        return new ResponseEntity<>(
                byUsername,
                HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(
            @RequestBody VillageUserLoginDto dto) {

        log.info("Login request received for username: {}",
                dto.getUsername());

        TokenDto tokenDto =
                userSignupService.login(dto);

        if (tokenDto != null) {

            log.info("Login successful for username: {}",
                    dto.getUsername());

            return new ResponseEntity<>(
                    tokenDto,
                    HttpStatus.OK);

        } else {

            log.warn("Login failed for username: {}",
                    dto.getUsername());

            return new ResponseEntity<>(
                    "Invalid password or Username",
                    HttpStatus.BAD_REQUEST);
        }
    }

    // session frontend gay delete krega usko feature banana hai
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {

        log.info("Logout request received");

        return ResponseEntity.ok(
                Collections.singletonMap(
                        "message",
                        "Logout successful"));
    }

    @GetMapping("/get-id/{id}")
    public ResponseEntity<VillageUserSignupDto> getUserDetailById(
            @PathVariable long id) {

        log.info("Fetching user details for id: {}", id);

        VillageUserSignupDto userDataById =
                userSignupService.getUserDataById(id);

        return new ResponseEntity<>(
                userDataById,
                HttpStatus.OK);
    }
}