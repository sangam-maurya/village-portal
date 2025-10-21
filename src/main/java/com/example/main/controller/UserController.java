package com.example.main.controller;

import com.example.main.payload.VillageUserSignupDto;
import com.example.main.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
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
    @PutMapping("/update")
    public ResponseEntity<VillageUserSignupDto> updateMyData(Authentication auth,
                                                             @Valid @RequestBody VillageUserSignupDto dto) {
        String username = auth.getName();
        VillageUserSignupDto updatedDto = userService.updateMyData(username, dto);
        return ResponseEntity.ok(updatedDto);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMyAccount(Authentication auth) {
        String username = auth.getName();
        userService.deleteMyAccount(username);
        return ResponseEntity.noContent().build();
    }

}
