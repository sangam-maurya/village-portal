package com.example.main.controller;

import com.example.main.service.EmailVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/verification")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(
            EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(
            @RequestParam String email) {

        emailVerificationService.saveOtp(email);

        return new ResponseEntity<>(
                "OTP sent successfully",
                HttpStatus.OK
        );
    }
    @PostMapping("/otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email , @RequestParam String otp){
        String result = emailVerificationService.verifyOtp(email, otp);
        return new ResponseEntity<>(result , HttpStatus.OK);
    }
}