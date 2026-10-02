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
    @PostMapping("/forget-pass")
    public ResponseEntity<?> forgetPass(@RequestParam String email ,
                                        @RequestParam String otp ,
                                        @RequestParam String newPassword){
        String string = emailVerificationService.forgetPassword(email, otp, newPassword);
        return new ResponseEntity<>(string , HttpStatus.OK);
    }
}