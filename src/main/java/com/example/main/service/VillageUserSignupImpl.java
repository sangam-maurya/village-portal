package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.EmailVerification;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.TokenDto;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.reposetry.EmailVerificationRepository;
import com.example.main.reposetry.VillageUserSignupRepository;
import com.example.main.service.Interface.VillageUserSignupService;
import org.modelmapper.ModelMapper;
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

    public VillageUserSignupImpl(VillageUserSignupRepository villageUserSignupRepository, ModelMapper mapper, JwtService jwtService, EmailVerificationService emailVerificationService, EmailVerificationRepository emailVerificationRepository) {
        this.villageUserSignupRepository = villageUserSignupRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.emailVerificationService = emailVerificationService;
        this.emailVerificationRepository = emailVerificationRepository;
    }

    @Override
    public VillageUserSignupDto cereateVillageUserSignup(VillageUserSignupDto dto){

        // 1️⃣ DTO → Entity
        VillageUserSignup villageUserSignup = mapper.map(dto, VillageUserSignup.class);

        // 2️⃣ Backend se createAt set karo (save se pehle)
        villageUserSignup.setCreateAt(LocalDateTime.now());
        if (villageUserSignupRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ResourceNotFound("username is already present");
        }
        if (villageUserSignupRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResourceNotFound("email is already present");
        }
        String hashpw = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(5));
        villageUserSignup.setPassword(hashpw);
        dto.setRole("USER");
        villageUserSignup.setRole(dto.getRole());


        Optional<EmailVerification> verification =
                emailVerificationRepository.findByEmail(dto.getEmail());

        if (verification.isEmpty() || !verification.get().isVerified()) {
            throw new ResourceNotFound("Please verify your email first");
        }

        // 3️⃣ DB me save karo
        VillageUserSignup savedEntity = villageUserSignupRepository.save(villageUserSignup);
        emailVerificationService.saveOtp(dto.getEmail());

        VillageUserSignupDto responseDto = mapper.map(savedEntity, VillageUserSignupDto.class);

        // 5️⃣ Optional (ensure consistency)
        responseDto.setId(savedEntity.getId());
        responseDto.setCreateAt(savedEntity.getCreateAt());
        return responseDto;
    }

    @Override
    public List<VillageUserSignupDto> getVillageUserSignup() {
        List<VillageUserSignup> all = villageUserSignupRepository.findAll();
        List<VillageUserSignupDto> list = all.stream().map(a -> mapper.map(a, VillageUserSignupDto.class)).toList();
        return list;
    }

    @Override
    public VillageUserSignupDto updateVillageUserSignup(VillageUserSignupDto dto, long id) {
        VillageUserSignup entity = villageUserSignupRepository.findById(id).orElseThrow(() -> new ResourceNotFound("id is not present"));
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
//        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        entity.setPhone(dto.getPhone());
        if (dto.getPassword()!=null && !dto.getPassword().isEmpty()){
            String hashpw = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(5));
            entity.setPassword(hashpw);
        }
        VillageUserSignup updatedEntity = villageUserSignupRepository.save(entity);
        VillageUserSignupDto responseDto = mapper.map(updatedEntity, VillageUserSignupDto.class);
        return responseDto;
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
    public VillageUserSignup findByEmail(String email) {
        VillageUserSignup villageUserSignup = villageUserSignupRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("email is not present " + email));
        return villageUserSignup;
    }

    @Override
    public TokenDto login(VillageUserLoginDto dto){
    Optional<VillageUserSignup> username =  villageUserSignupRepository.findByUsername(dto.getUsername());
    if (username.isPresent()){
        VillageUserSignup villageUserSignup = username.get();
    if (BCrypt.checkpw(dto.getPassword(), villageUserSignup.getPassword())){
        String token = jwtService.generateToken(dto.getUsername());
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);
        tokenDto.setJwt("JWT TYPE Token");
        tokenDto.setRole(villageUserSignup.getRole());
        tokenDto.setFullName(villageUserSignup.getFullName());
        return tokenDto;
    }else {
        return null;
    }
    }else {
            return null;
    }
    }

}
