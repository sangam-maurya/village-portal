package com.example.main.service;

import com.example.main.entity.VillageUserSignup;
import com.example.main.reposetry.VillageUserSignupRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetail implements UserDetailsService {
    private final VillageUserSignupRepository villageUserSignupRepository;

    public CustomUserDetail(VillageUserSignupRepository villageUserSignupRepository) {
        this.villageUserSignupRepository = villageUserSignupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        VillageUserSignup user = villageUserSignupRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        String role = user.getRole() == null ? "USER" : user.getRole().replace("ROLE_", "");
       return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}
