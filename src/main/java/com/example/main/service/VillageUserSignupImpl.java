package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.TokenDto;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.VillageUserSignupDto;
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

    private static final Logger log =
            LoggerFactory.getLogger(VillageUserSignupImpl.class);

    private final VillageUserSignupRepository villageUserSignupRepository;
    private final ModelMapper mapper;
    private final JwtService jwtService;

    public VillageUserSignupImpl(
            VillageUserSignupRepository villageUserSignupRepository,
            ModelMapper mapper,
            JwtService jwtService) {

        this.villageUserSignupRepository = villageUserSignupRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
    }

    @Override
    public List<VillageUserSignupDto> getVillageUserSignup() {

        log.info("Fetching all users");

        List<VillageUserSignup> all =
                villageUserSignupRepository.findAll();
        return all.stream()
                .map(user -> mapper.map(user, VillageUserSignupDto.class))
                .toList();
    }

    @Override
    public void deleteVillageUserSignup(long id) {

        log.info("Deleting user with id: {}", id);

        VillageUserSignup villageUserSignup =
                villageUserSignupRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFound("id is not present " + id));

        villageUserSignupRepository.delete(villageUserSignup);

        log.info("User deleted successfully with id: {}", id);
    }

    @Override
    public VillageUserSignup findByUsername(String username) {

        log.info("Finding user by username: {}", username);

        return villageUserSignupRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "Username is not present " + username));
    }

    @Override
    public TokenDto login(VillageUserLoginDto dto) {

        log.info("Login attempt for username: {}", dto.getUsername());

        Optional<VillageUserSignup> username =
                villageUserSignupRepository.findByUsername(dto.getUsername());

        if (username.isPresent()) {

            VillageUserSignup villageUserSignup = username.get();

            if (BCrypt.checkpw(
                    dto.getPassword(),
                    villageUserSignup.getPassword())) {

                log.info("Password verified successfully for username: {}",
                        dto.getUsername());

                String token =
                        jwtService.generateToken(dto.getUsername());

                TokenDto tokenDto = new TokenDto();

                tokenDto.setToken(token);
                tokenDto.setJwt("JWT TYPE Token");
                tokenDto.setRole(villageUserSignup.getRole());
                tokenDto.setFullName(villageUserSignup.getFullName());

                log.info("Login successful for username: {}",
                        dto.getUsername());

                return tokenDto;

            } else {

                log.warn("Invalid password for username: {}",
                        dto.getUsername());

                return null;
            }

        } else {

            log.warn("Username not found: {}", dto.getUsername());

            return null;
        }
    }

    @Override
    public VillageUserSignupDto getUserDataById(long id) {

        log.info("Fetching user data for id: {}", id);

        VillageUserSignup villageUserSignup =
                villageUserSignupRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFound(
                                        "Id is not present " + id));

        return mapper.map(
                villageUserSignup,
                VillageUserSignupDto.class);
    }

    @Override
    public long getCount() {

        log.info("Fetching total user count");

        long count = villageUserSignupRepository.count();
        return count;
    }

    @Override
    public VillageUserSignupDto cereateVillageUserSignup(
            VillageUserSignupDto dto,
            MultipartFile profileImage) throws IOException {

        log.info("Starting user signup for username: {}",
                dto.getUsername());

        VillageUserSignup villageUserSignup =
                mapper.map(dto, VillageUserSignup.class);

        if (profileImage != null && !profileImage.isEmpty()) {

            log.info("Profile image received for username: {}",
                    dto.getUsername());

            villageUserSignup.setProfileImage(
                    profileImage.getBytes());
        }

        villageUserSignup.setCreateAt(LocalDateTime.now());

        log.info("Checking username availability");

        if (villageUserSignupRepository
                .findByUsername(dto.getUsername())
                .isPresent()) {

            log.warn("Username already exists: {}",
                    dto.getUsername());

            throw new ResourceNotFound(
                    "username is already present");
        }

        log.info("Checking email availability");

        if (villageUserSignupRepository
                .findByEmail(dto.getEmail())
                .isPresent()) {

            log.warn("Email already exists: {}",
                    dto.getEmail());

            throw new ResourceNotFound(
                    "email is already present");
        }

        log.info("Hashing password");

        String hashpw =
                BCrypt.hashpw(
                        dto.getPassword(),
                        BCrypt.gensalt(5));

        villageUserSignup.setPassword(hashpw);

        dto.setRole("USER");
        villageUserSignup.setRole(dto.getRole());

        log.info("Saving user information");

        VillageUserSignup savedEntity =
                villageUserSignupRepository.save(villageUserSignup);

        VillageUserSignupDto responseDto =
                mapper.map(
                        savedEntity,
                        VillageUserSignupDto.class);

        responseDto.setId(savedEntity.getId());
        responseDto.setCreateAt(savedEntity.getCreateAt());

        log.info("User signup completed successfully for username: {}",
                dto.getUsername());
        return responseDto;
    }
}