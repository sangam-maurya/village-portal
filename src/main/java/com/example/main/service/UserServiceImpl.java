
package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.reposetry.VillageUserSignupRepository;
import com.example.main.service.Interface.UserService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private final VillageUserSignupRepository villageUserSignupRepository;
    private final ModelMapper mapper;

    public UserServiceImpl(
            VillageUserSignupRepository villageUserSignupRepository,
            ModelMapper mapper) {
        this.villageUserSignupRepository = villageUserSignupRepository;
        this.mapper = mapper;
    }

    @Override
    public VillageUserSignupDto getMyData(String username) {

        log.info("Fetching user profile");

        VillageUserSignup user =
                villageUserSignupRepository.findByUsername(username)
                        .orElseThrow(() -> {
                            log.warn("User profile not found");
                            return new ResourceNotFound("User not found");
                        });

        log.debug("User profile fetched successfully. User ID: {}", user.getId());

        return mapper.map(user, VillageUserSignupDto.class);
    }

    @Override
    public VillageUserSignupDto updateMyData(
            String username,
            VillageUserSignupDto dto,
            MultipartFile profileImage) throws IOException {

        log.info("User profile update requested");

        VillageUserSignup user =
                villageUserSignupRepository.findByUsername(username)
                        .orElseThrow(() -> {
                            log.warn("Profile update failed: user not found");
                            return new ResourceNotFound("User not found");
                        });

        // Update profile image if provided
        if (profileImage != null && !profileImage.isEmpty()) {
            user.setProfileImage(profileImage.getBytes());
            log.info("Profile image updated");
        }

        // Update full name if provided
        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
            user.setFullName(dto.getFullName());
            log.debug("Full name updated");
        }

        // Update phone if provided
        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            user.setPhone(dto.getPhone());
            log.debug("Phone number updated");
        }

        // Update email if provided
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            user.setEmail(dto.getEmail());
            log.debug("Email updated");
        }

        // Update username if provided
        if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
            user.setUsername(dto.getUsername());
            log.debug("Username updated");
        }

        VillageUserSignup saved =
                villageUserSignupRepository.save(user);

        VillageUserSignupDto response =
                mapper.map(saved, VillageUserSignupDto.class);

        log.info(
                "User profile updated successfully. User ID: {}, Profile image bytes: {}",
                saved.getId(),
                response.getProfileImage() == null
                        ? 0
                        : response.getProfileImage().length
        );

        return response;
    }

    @Override
    public void deleteMyAccount(String username) {

        log.info("Account deletion requested");

        VillageUserSignup user =
                villageUserSignupRepository.findByUsername(username)
                        .orElseThrow(() -> {
                            log.warn("Account deletion failed: user not found");
                            return new ResourceNotFound("User not found");
                        });

        villageUserSignupRepository.delete(user);

        log.info("Account deleted successfully. User ID: {}", user.getId());
    }
}
