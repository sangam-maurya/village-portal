
package com.example.main.service.Interface;

import com.example.main.payload.NotificationDto;
import java.util.List;

public interface NotificationService {

    List<NotificationDto> getMyNotifications(String username);

    long getUnreadCount(String username);

    NotificationDto markAsRead(Long notificationId, String username);


    void markAllAsRead(String username);

}
