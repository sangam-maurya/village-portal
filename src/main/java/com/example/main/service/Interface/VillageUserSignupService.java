package com.example.main.service.Interface;

import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.TokenDto;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.VillageUserSignupDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VillageUserSignupService {

VillageUserSignupDto cereateVillageUserSignup(VillageUserSignupDto VillageUserSignupDto) ;
List<VillageUserSignupDto> getVillageUserSignup();
VillageUserSignupDto updateVillageUserSignup(VillageUserSignupDto VillageUserSignupDto , long id);
void deleteVillageUserSignup(long id);
VillageUserSignup findByUsername(String username);
VillageUserSignup findByEmail(String email);
//String verifyLogin(VillageUserLoginDto dto);
    TokenDto login(VillageUserLoginDto dto);
}
