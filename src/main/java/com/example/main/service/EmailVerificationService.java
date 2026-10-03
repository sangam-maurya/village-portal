package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.EmailVerification;
import com.example.main.entity.VillageUserSignup;
import com.example.main.reposetry.EmailVerificationRepository;
import com.example.main.reposetry.VillageUserSignupRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final VillageUserSignupRepository repository;

    private final EmaillService emaillService;

    public EmailVerificationService(EmailVerificationRepository emailVerificationRepository, VillageUserSignupRepository repository, EmaillService emaillService) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.repository = repository;
        this.emaillService = emaillService;
    }


    public String saveOtp(String email) {
        Optional<VillageUserSignup> signup = repository.findByEmail(email);

        if (signup.isEmpty()) {
            throw new ResourceNotFound("Email is not registered please enter correct email ");
        }

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
                "Village Portal - Password Reset OTP",
                "Dear User,\n\n"
                        + "We received a request to reset your Village Portal password.\n\n"
                        + "Your One-Time Password (OTP) is: " + otp + "\n\n"
                        + "This OTP is valid for 5 minutes. Please do not share this OTP with anyone.\n\n"
                        + "If you did not request a password reset, please ignore this email.\n\n"
                        + "Regards,\n"
                        + "Village Portal Team"
        );

        return otp;
    }

    public String generateOtp() {
        return String.valueOf(
                ThreadLocalRandom.current()
                        .nextInt(100000, 1000000)
        );
    }
    public  String verifyOtp(String email , String otp) throws BadRequestException {

        Optional<VillageUserSignup> signup = repository.findByEmail(email);
        if (signup.isEmpty()){
            throw  new ResourceNotFound("Email is not registered please enter correct email ");
        }


        Optional<EmailVerification> verification = emailVerificationRepository.findByEmail(email);

        if (verification.isEmpty()){
            throw  new ResourceNotFound("email is not present please user correct email ");
        }
        EmailVerification data = verification.get();
        if (!data.getOtp().equals(otp)){
           throw new BadRequestException("invalid otp please enter correct email ");
        }
        if (data.getExpiryTime().isBefore(LocalDateTime.now())){
           throw new BadRequestException("Otp Expire");
        }
        data.setVerified(true);

        emailVerificationRepository.save(data);
        return "Otp verified successfully";
    }

    public String forgetPassword(String email , String otp, String newPassword) throws BadRequestException {
        String result = verifyOtp(email, otp);
        VillageUserSignup signup = repository.findByEmail(email).get();

        String hashpw = BCrypt.hashpw(newPassword, BCrypt.gensalt(5));
        signup.setPassword(hashpw);
        repository.save(signup);
        return "password reset succesfully";
    }

}