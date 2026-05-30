package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.Notification;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.notification.NotificationRequest;


public interface NotificationService {

    ResponseMessage<BaseResult> pushNotification(Notification notification);

    ResponseMessage<BaseResult> insertNotification(NotificationRequest notificationRequest);

}