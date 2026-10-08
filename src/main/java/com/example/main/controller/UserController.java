package com.example.main.controller;

import com.example.main.payload.VillageUserSignupDto;
import com.example.main.service.Interface.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/find")
    public ResponseEntity<VillageUserSignupDto> getMyData(Authentication auth) {
        String username = auth.getName();
        VillageUserSignupDto dto = userService.getMyData(username);
        return ResponseEntity.ok(dto);
    }
    @PutMapping(
            value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<VillageUserSignupDto> updateMyData(
            Authentication auth,
            @RequestPart("user") String userJson,
            @RequestParam(value = "profileImage", required = false)
            MultipartFile profileImage) throws Exception {

        String username = auth.getName();

        ObjectMapper objectMapper = new ObjectMapper();

        VillageUserSignupDto dto =
                objectMapper.readValue(userJson, VillageUserSignupDto.class);

        VillageUserSignupDto updatedDto =
                userService.updateMyData(username, dto, profileImage);

        return ResponseEntity.ok(updatedDto);

    }    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMyAccount(Authentication auth) {
        String username = auth.getName();
        userService.deleteMyAccount(username);
        return new ResponseEntity<>("user delete successfully " , HttpStatus.OK);
    }
}
