package com.example.main.service;

import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.reposetry.VillageUserSignupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class UserServiceImpl implements UserService{
    private final VillageUserSignupRepository villageUserSignupRepository;
    private final ModelMapper mapper;
    public UserServiceImpl(VillageUserSignupRepository villageUserSignupRepository, ModelMapper mapper) {
        this.villageUserSignupRepository = villageUserSignupRepository;
        this.mapper = mapper;
    }

    @Override
    public VillageUserSignupDto getMyData(String username) {
        VillageUserSignup user = villageUserSignupRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.map(user, VillageUserSignupDto.class);
    }

    @Override
    public VillageUserSignupDto updateMyData(String username, VillageUserSignupDto dto) {
        VillageUserSignup user = villageUserSignupRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields
        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());

        // Password encryption
        if(dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(5)));
        }

        user.setCreateAt(LocalDateTime.now()); // optional, if you want update time

        VillageUserSignup saved = villageUserSignupRepository.save(user);
        return mapper.map(saved, VillageUserSignupDto.class);
    }

    @Override
    public void deleteMyAccount(String username) {
        VillageUserSignup user = villageUserSignupRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        villageUserSignupRepository.delete(user);
    }
}
