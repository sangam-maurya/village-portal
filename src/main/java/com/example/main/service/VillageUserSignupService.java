package com.example.main.service;

import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.VillageUserLoginDto;
import com.example.main.payload.VillageUserSignupDto;

import java.util.List;

public interface VillageUserSignupService {

VillageUserSignupDto cereateVillageUserSignup(VillageUserSignupDto VillageUserSignupDto);
List<VillageUserSignupDto> getVillageUserSignup();
VillageUserSignupDto updateVillageUserSignup(VillageUserSignupDto VillageUserSignupDto , long id);
void deleteVillageUserSignup(long id);
VillageUserSignup findByUsername(String username);
VillageUserSignup findByEmail(String email);
String verifyLogin(VillageUserLoginDto dto);
}
