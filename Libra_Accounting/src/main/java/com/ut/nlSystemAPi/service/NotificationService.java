package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.Notification;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.Notification.UserDeviceNotificationRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface NotificationService {

  ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(UserDeviceNotificationRequest userDeviceNotificationRequest);

  ResponseMessage<BaseResult> readAll(HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> pushNotification(Notification notification);

}