package com.example.main.service;

import com.example.main.entity.VillageUserSignup;
import com.example.main.reposetry.VillageUserSignupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VillageUserSignupServiceTest {
    @Mock
    private VillageUserSignupRepository repository;
    @InjectMocks
    private VillageUserSignupImpl service;

    @Test
    public void testByUsername(){
        VillageUserSignup villageUserSignup = new VillageUserSignup();
        villageUserSignup.setId(1L);
        villageUserSignup.setUsername("Sangam");
        villageUserSignup.setEmail("sangammaurya648@gmail.com");
        villageUserSignup.setPassword("Sangam@123");
        villageUserSignup.setFullName("Sangam Maurya");
        villageUserSignup.setPhone("1234567890");

        when(repository.findByUsername("Sangam")).thenReturn(Optional.of(villageUserSignup));
        VillageUserSignup byUsername = service.findByUsername("Sangam");
        assertNotNull(byUsername);
        assertEquals("Sangam",byUsername.getUsername());
        System.out.println(byUsername.getUsername());
    }
}
