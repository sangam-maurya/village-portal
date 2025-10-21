package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.VillageUserSignupDto;
import com.example.main.reposetry.VillageUserSignupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VillageUserSignupImpl implements VillageUserSignupService {
    private final VillageUserSignupRepository villageUserSignupRepository;
    private final ModelMapper mapper;

    public VillageUserSignupImpl(VillageUserSignupRepository villageUserSignupRepository, ModelMapper mapper) {
        this.villageUserSignupRepository = villageUserSignupRepository;
        this.mapper = mapper;
    }

    @Override
    public VillageUserSignupDto cereateVillageUserSignup(VillageUserSignupDto dto) {

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
        // 3️⃣ DB me save karo
        VillageUserSignup savedEntity = villageUserSignupRepository.save(villageUserSignup);

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
        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        entity.setPhone(dto.getPhone());

        VillageUserSignup save = villageUserSignupRepository.save(entity);
        return mapper.map(save , VillageUserSignupDto.class);


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
    public String verifyLogin(VillageUserLoginDto dto) {
        VillageUserSignup login = villageUserSignupRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new ResourceNotFound("username is not present " + dto.getUsername()));
        boolean checkpw = BCrypt.checkpw(dto.getPassword(), login.getPassword());
        if (checkpw) {
            return "login successful";
        } else {
            return "password is incorrect";
        }
    }

}
