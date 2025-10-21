package com.example.main.service;

import com.example.main.payload.VillageUserSignupDto;

public interface UserService {
    // USER apna data dekh sake
    VillageUserSignupDto getMyData(String username);

    // USER apni details update kar sake
    VillageUserSignupDto updateMyData(String username, VillageUserSignupDto dto);

    // USER apni account delete kar sake
    void deleteMyAccount(String username);
}
