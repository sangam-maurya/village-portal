package com.example.main.controller;

import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.service.VillageUserSignupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class VillageUserSignupController {

    private final VillageUserSignupService userSignupService;

    public VillageUserSignupController(VillageUserSignupService userSignupService) {
        this.userSignupService = userSignupService;
    }

    @PostMapping("/create")
    public ResponseEntity<VillageUserSignupDto> createUser(@Valid @RequestBody VillageUserSignupDto villageUserSignupDto){
        VillageUserSignupDto villageUserSignupDto1 = userSignupService.cereateVillageUserSignup(villageUserSignupDto);
        return new ResponseEntity<>(villageUserSignupDto1 , HttpStatus.CREATED);
    }
    @GetMapping("/find-all")
    public ResponseEntity<List<VillageUserSignupDto>> getAllData(){
        List<VillageUserSignupDto> villageUserSignup = userSignupService.getVillageUserSignup();
        return new ResponseEntity<>(villageUserSignup , HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<VillageUserSignupDto>updateData(@PathVariable Long id , @RequestBody VillageUserSignupDto dto){
        VillageUserSignupDto villageUserSignupDto = userSignupService.updateVillageUserSignup(dto, id);
        return new ResponseEntity<>(villageUserSignupDto , HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        userSignupService.deleteVillageUserSignup(id);
        return new ResponseEntity<>("id is deleted " + id , HttpStatus.OK);
    }
    @GetMapping("/by-username")
    public ResponseEntity<VillageUserSignup> findUsername(@RequestParam ("username") String username){
        VillageUserSignup byUsername = userSignupService.findByUsername(username);
        return new ResponseEntity<>(byUsername , HttpStatus.OK);
    }
    @GetMapping("/by-email")
    public ResponseEntity<VillageUserSignup> findEmail(@RequestParam("email") String email){
        VillageUserSignup byEmail = userSignupService.findByEmail(email);
        return new ResponseEntity<>(byEmail , HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> verifyLogin(@RequestBody VillageUserLoginDto dto) {
        String s = userSignupService.verifyLogin(dto);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}

