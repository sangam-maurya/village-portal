
package com.example.main.reposetry;

import com.example.main.entity.Notification;
import com.example.main.entity.VillageUserSignup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedAtDesc(
            VillageUserSignup user);

    long countByUserAndReadFalse(VillageUserSignup user);

    List<Notification> findByUserAndReadFalse(
            VillageUserSignup user);

}
