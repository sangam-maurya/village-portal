package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.reposetry.VillageUserSignupRepository;
import com.example.main.service.Interface.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class UserServiceImpl implements UserService {
    private  final VillageUserSignupRepository villageUserSignupRepository;

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
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
            user.setFullName(dto.getFullName());
        }

        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            user.setPhone(dto.getPhone());
        }

        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String hashpw = BCrypt.hashpw(
                    dto.getPassword(),
                    BCrypt.gensalt(5)
            );
            user.setPassword(hashpw);
        }

        VillageUserSignup saved =
                villageUserSignupRepository.save(user);

        return mapper.map(saved, VillageUserSignupDto.class);
    }
    @Override
    public void deleteMyAccount(String username) {
        VillageUserSignup user = villageUserSignupRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        villageUserSignupRepository.delete(user);
    }

}
