package com.example.main.service.Interface;

import com.example.main.payload.VillageUserSignupDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    // USER apna data dekh sake
    VillageUserSignupDto getMyData(String username);

    // USER apni details update kar sake
    VillageUserSignupDto updateMyData(String username, VillageUserSignupDto dto , MultipartFile profileImage) throws IOException;

    // USER apni account delete kar sake
    void deleteMyAccount(String username);
}
