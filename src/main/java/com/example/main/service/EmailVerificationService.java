package com.example.main.service;

import com.example.main.entity.EmailVerification;
import com.example.main.reposetry.EmailVerificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;


    private final EmaillService emaillService;

    public EmailVerificationService(EmailVerificationRepository emailVerificationRepository, EmaillService emaillService) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.emaillService = emaillService;
    }


    public String saveOtp(String email) {

        String otp = generateOtp();

        Optional<EmailVerification> existing = emailVerificationRepository.findByEmail(email);

        EmailVerification verification;

        if (existing.isPresent()){
           verification= existing.get();
        }else {
            verification =new EmailVerification();
            verification.setEmail(email);
        }

        verification.setOtp(otp);
        verification.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        verification.setVerified(false);

        emailVerificationRepository.save(verification);

        emaillService.sendMsg(
                email,
                "Village Portal Email Verification",
                "Your Village  OTP is: " + otp +
                        "\nThis OTP is valid for 5 minutes."
        );

        return otp;
    }

    public String generateOtp() {
        return String.valueOf(
                ThreadLocalRandom.current()
                        .nextInt(100000, 1000000)
        );
    }
    public  String verifyOtp(String email , String otp){

        Optional<EmailVerification> verification = emailVerificationRepository.findByEmail(email);

        if (verification.isEmpty()){
            return "otp not found ";
        }
        EmailVerification data = verification.get();
        if (!data.getOtp().equals(otp)){
            return "Invalid Otp";
        }
        if (data.getExpiryTime().isBefore(LocalDateTime.now())){
            return  "otp expired";
        }
        data.setVerified(true);

        emailVerificationRepository.save(data);
        return "Otp verified successfully";
    }

}