package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.notification.NotificationRecipientRequest;
import com.ut.nlSystemAPi.model.notification.NotificationRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationMapper {

  List<String> getDeviceToken(@Param("employeeId") Long employeeId, @Param("userId") Long userId);

  Boolean insertNotification(@Param("notification") NotificationRequest notification);

  Boolean insertNotificationRecipients(@Param("notificationRecipientRequest") NotificationRecipientRequest notificationRecipientRequest);

}
