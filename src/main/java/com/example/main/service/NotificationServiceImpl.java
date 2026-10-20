
package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.Notification;
import com.example.main.entity.VillageUserSignup;
import com.example.main.payload.NotificationDto;
import com.example.main.reposetry.NotificationRepository;
import com.example.main.reposetry.VillageUserSignupRepository;
import com.example.main.service.Interface.NotificationService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final VillageUserSignupRepository userRepository;
    private final ModelMapper mapper;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            VillageUserSignupRepository userRepository,
            ModelMapper mapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getMyNotifications(String username) {
        VillageUserSignup user = getUser(username);

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(notification ->
                        mapper.map(notification, NotificationDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(String username) {
        VillageUserSignup user = getUser(username);

        return notificationRepository.countByUserAndReadFalse(user);
    }

    @Override
    public NotificationDto markAsRead(Long notificationId, String username) {
        VillageUserSignup user = getUser(username);

        Notification notification = notificationRepository
                .findById(notificationId)
                .filter(item -> item.getUser().getId().equals(user.getId()))
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "Notification not found with id: " + notificationId));

        notification.setRead(true);

        Notification savedNotification =
                notificationRepository.save(notification);

        return mapper.map(savedNotification, NotificationDto.class);
    }

    private VillageUserSignup getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFound("User not found: " + username));
    }

    @Override
    public void markAllAsRead(String username) {
        VillageUserSignup user = getUser(username);

        List<Notification> notifications =
                notificationRepository.findByUserAndReadFalse(user);

        notifications.forEach(notification -> notification.setRead(true));

        notificationRepository.saveAll(notifications);
    }

}
