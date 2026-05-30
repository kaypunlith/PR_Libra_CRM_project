package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.PushNotificationOneSignal;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.NotificationMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.Notification;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.notification.NotificationRecipientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ut.nlSystemAPi.model.notification.NotificationRequest;

import java.time.LocalTime;

@Service
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private NotificationMapper notificationMapper;

  @Autowired
  private MessageService messageService;

  @Autowired
  private ActivityLogService activityLogService;

  public ResponseMessage<BaseResult> pushNotification(Notification notification) {
    PushNotificationOneSignal.pushAndroid(notification.getDeviceTokens(), notification.getContent(), notification.getMessage());
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
  }

  public ResponseMessage<BaseResult> insertNotification(NotificationRequest notificationRequest){
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {

      Boolean result = notificationMapper.insertNotification(notificationRequest);

      if (result) {

        //!Insert Notification Recipients
        if(notificationRequest.getNotificationRecipientRequests() != null && notificationRequest.getNotificationRecipientRequests().size() > 0) {
          for (int i = 0; i < notificationRequest.getNotificationRecipientRequests().size(); i++) {
            NotificationRecipientRequest notificationRecipientRequest = new NotificationRecipientRequest();
            notificationRecipientRequest.setNotificationId(notificationRequest.getId());
            notificationRecipientRequest.setRecipientId(notificationRequest.getNotificationRecipientRequests().get(i).getRecipientId());
            notificationMapper.insertNotificationRecipients(notificationRecipientRequest);
          }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      System.out.println(error);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

}