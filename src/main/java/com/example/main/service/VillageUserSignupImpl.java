package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.EmailVerification;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.TokenDto;
import com.example.main.payload.VillageProjectDto;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.reposetry.EmailVerificationRepository;
import com.example.main.reposetry.VillageUserSignupRepository;
import com.example.main.service.Interface.VillageUserSignupService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VillageUserSignupImpl implements VillageUserSignupService {
    private final VillageUserSignupRepository villageUserSignupRepository;
    private final ModelMapper mapper;
    private final JwtService jwtService;
    private final EmailVerificationService emailVerificationService;
    private final EmailVerificationRepository emailVerificationRepository;
    private static final Logger log =
            LoggerFactory.getLogger(EmailVerificationService.class);

    public VillageUserSignupImpl(VillageUserSignupRepository villageUserSignupRepository, ModelMapper mapper, JwtService jwtService, EmailVerificationService emailVerificationService, EmailVerificationRepository emailVerificationRepository) {
        this.villageUserSignupRepository = villageUserSignupRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.emailVerificationService = emailVerificationService;
        this.emailVerificationRepository = emailVerificationRepository;
    }

//    @Override
//    public VillageUserSignupDto cereateVillageUserSignup(VillageUserSignupDto dto){
//
//        // 1️⃣ DTO → Entity
//        VillageUserSignup villageUserSignup = mapper.map(dto, VillageUserSignup.class);
//
//        log.info("Setting user creation time");
//        // 2️⃣ Backend se createAt set karo (save se pehle)
//        villageUserSignup.setCreateAt(LocalDateTime.now());
//        if (villageUserSignupRepository.findByUsername(dto.getUsername()).isPresent()) {
//            throw new ResourceNotFound("username is already present");
//        }
//        log.info("Checking email verification status");
//        if (villageUserSignupRepository.findByEmail(dto.getEmail()).isPresent()) {
//            throw new ResourceNotFound("email is already present");
//        }
//        log.info("hashing password");
//        String hashpw = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(5));
//        villageUserSignup.setPassword(hashpw);
//        dto.setRole("USER");
//        villageUserSignup.setRole(dto.getRole());
//
//        log.info("saving user info");
//        // 3️⃣ DB me save karo
//        VillageUserSignup savedEntity = villageUserSignupRepository.save(villageUserSignup);
//        VillageUserSignupDto responseDto = mapper.map(savedEntity, VillageUserSignupDto.class);
//
//        // 5️⃣ Optional (ensure consistency)
//        responseDto.setId(savedEntity.getId());
//        responseDto.setCreateAt(savedEntity.getCreateAt());
//        return responseDto;
//    }


    @Override
    public List<VillageUserSignupDto> getVillageUserSignup() {
        List<VillageUserSignup> all = villageUserSignupRepository.findAll();
        List<VillageUserSignupDto> list = all.stream().map(a -> mapper.map(a, VillageUserSignupDto.class)).toList();
        return list;
    }

    @Override
    public void deleteVillageUserSignup(long id) {
        VillageUserSignup villageUserSignup = villageUserSignupRepository.findById(id).orElseThrow(() -> new ResourceNotFound("id is not present " + id));
        villageUserSignupRepository.delete(villageUserSignup);
    }

    @Override
    public VillageUserSignup findByUsername(String username) {
        VillageUserSignup villageUserSignup = villageUserSignupRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFound("Username is not present " + username));
        return villageUserSignup;
    }

    @Override
    public TokenDto login(VillageUserLoginDto dto) {
        Optional<VillageUserSignup> username = villageUserSignupRepository.findByUsername(dto.getUsername());
        if (username.isPresent()) {
            VillageUserSignup villageUserSignup = username.get();
            if (BCrypt.checkpw(dto.getPassword(), villageUserSignup.getPassword())) {
                String token = jwtService.generateToken(dto.getUsername());
                TokenDto tokenDto = new TokenDto();
                tokenDto.setToken(token);
                tokenDto.setJwt("JWT TYPE Token");
                tokenDto.setRole(villageUserSignup.getRole());
                tokenDto.setFullName(villageUserSignup.getFullName());
                return tokenDto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public VillageUserSignupDto getUserDataById(long id) {
        Optional<VillageUserSignup> userSignup = villageUserSignupRepository.findById(id);
        if (userSignup.isEmpty()) {
            throw new ResourceNotFound("Id is not present " + id);
        }
        VillageUserSignup villageUserSignup = userSignup.get();
        VillageUserSignupDto map = mapper.map(villageUserSignup, VillageUserSignupDto.class);
        return map;
    }

    @Override
    public long getCount() {
        long count = villageUserSignupRepository.count();
        return count;
    }

    @Override
    public VillageUserSignupDto cereateVillageUserSignup(
            VillageUserSignupDto dto,
            MultipartFile profileImage) throws IOException {

        // 1️⃣ DTO → Entity
        VillageUserSignup villageUserSignup =
                mapper.map(dto, VillageUserSignup.class);

        // 2️⃣ Profile image save karo
        if (profileImage != null && !profileImage.isEmpty()) {
            villageUserSignup.setProfileImage(profileImage.getBytes());
        }

        log.info("Setting user creation time");

        // 3️⃣ Backend se createAt set karo
        villageUserSignup.setCreateAt(LocalDateTime.now());

        if (villageUserSignupRepository
                .findByUsername(dto.getUsername())
                .isPresent()) {

            throw new ResourceNotFound("username is already present");
        }

        log.info("Checking email verification status");

        if (villageUserSignupRepository
                .findByEmail(dto.getEmail())
                .isPresent()) {

            throw new ResourceNotFound("email is already present");
        }

        log.info("hashing password");

        String hashpw =
                BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(5));

        villageUserSignup.setPassword(hashpw);

        dto.setRole("USER");
        villageUserSignup.setRole(dto.getRole());

        log.info("saving user info");

        // 4️⃣ DB me save karo
        VillageUserSignup savedEntity =
                villageUserSignupRepository.save(villageUserSignup);

        VillageUserSignupDto responseDto =
                mapper.map(savedEntity, VillageUserSignupDto.class);

        // 5️⃣ Optional
        responseDto.setId(savedEntity.getId());
        responseDto.setCreateAt(savedEntity.getCreateAt());

        return responseDto;
    }


}
