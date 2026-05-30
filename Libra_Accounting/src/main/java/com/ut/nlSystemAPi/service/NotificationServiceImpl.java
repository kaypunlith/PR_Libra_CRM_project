package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.PushNotificationOneSignal;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.NotificationMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.Notification;
import com.ut.nlSystemAPi.model.UserDeviceNotification;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.Notification.UserDeviceNotificationRequest;
import com.ut.nlSystemAPi.model.response.Notification.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    Environment environment;

    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(notificationMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<NotificationResponse > notificationResponses = notificationMapper.getList(filter);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/notification/list",null,null,"notification","notification (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", notificationResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/notification/list",line, error.toString(),"notification","notification (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(UserDeviceNotificationRequest userDeviceNotificationRequest) {
        Long userId = userService.getUserAuth().getId();
        Boolean result;

        // Check Data
        UserDeviceNotification userDeviceNotification = new UserDeviceNotification();
        userDeviceNotification.setUserId(userId);
        userDeviceNotification.setLanguageId(userDeviceNotificationRequest.getLanguageId());
        userDeviceNotification.setUsername(UserAuthSession.getUserAuth().getUsername());
        userDeviceNotification.setAppType(userDeviceNotificationRequest.getAppType());
        userDeviceNotification.setDeviceId(userDeviceNotificationRequest.getDeviceId());
        userDeviceNotification.setDeviceName(userDeviceNotificationRequest.getDeviceName());
        userDeviceNotification.setDeviceToken(userDeviceNotificationRequest.getDeviceToken());
        userDeviceNotification.setIsActive(1);

        // Check Duplicate
        if(notificationMapper.checkDuplicate(userId,userDeviceNotificationRequest.getDeviceId(),userDeviceNotificationRequest.getDeviceToken()) > 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        }else {
            result = notificationMapper.insertUserDeviceToken(userDeviceNotification);
        }

        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> readAll(HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (Delete)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Boolean result = notificationMapper.readAll(userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/notification/readAll",null,null,"notification","notification (readAll)","readAll",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/notification/readAll",line, error.toString(),"notification","notification (readAll)","readAll",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> pushNotification(Notification notification) {
        //! Push Notification
        List<String> deviceTokens = notificationMapper.getDeviceToken();
        String message = notification.getMessage();
        String content = notification.getContent();

        if(deviceTokens.size() > 0) {
                PushNotificationOneSignal.pushAndroid(deviceTokens, content, message, notification.getFeedId());
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    }

}