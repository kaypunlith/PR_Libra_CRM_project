package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.TelegramNotificationMapper;
import com.ut.nlSystemAPi.model.Department;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.TelegramNotification;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.request.TelegramNotificationRequest;
import com.ut.nlSystemAPi.model.request.TelegramNotificationUpdateRequest;
import com.ut.nlSystemAPi.model.response.TelegramNotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramNotificationServiceImpl implements TelegramNotificationService {

  @Autowired
  private TelegramNotificationMapper telegramNotificationMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private PermissionMapper permissionMapper;


  public ResponseMessage<BaseResult> getList(Filter filter) {
    try {
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Telegram Notification (View)") == 0) {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
      }

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
      pagination.setTotal(telegramNotificationMapper.countList(filter));

      List<TelegramNotificationResponse> telegramNotificationResponses = telegramNotificationMapper.getList(filter);
      if (!telegramNotificationResponses.isEmpty()) {
        for (TelegramNotificationResponse response : telegramNotificationResponses) {
          response.setDepartments(telegramNotificationMapper.getDepartment(response.getId()));
        }
      }

      return ResponseMessageUtils.makeResponse(true, messageService.message("success", telegramNotificationResponses, pagination, true));
    } catch (Exception e) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  @Override
  public ResponseMessage<BaseResult> getOne(Long id) {
    try {
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Telegram Notification (View)") == 0) {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
      }

      List<TelegramNotificationResponse> telegramNotificationResponses = this.telegramNotificationMapper.getOne(id);
      if (!telegramNotificationResponses.isEmpty()) {
        for (TelegramNotificationResponse response : telegramNotificationResponses) {
          response.setDepartments(telegramNotificationMapper.getDepartment(response.getId()));
        }
      }
      return ResponseMessageUtils.makeResponse(true, this.messageService.message("success", telegramNotificationResponses, true));
    } catch (Exception e) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }

  }

  @Override
  public ResponseMessage<BaseResult> insert(TelegramNotificationRequest request, Long creatorId) {
    Long userId = creatorId;
    if (permissionMapper.checkPermission(userId, "Telegram Notification (Add)") == 0) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
    }

    // * Check Duplicate
    if (telegramNotificationMapper.checkDuplicate(request.getChatId(), null) > 0) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Telegram Chat Id.", false));
    }

    TelegramNotification telegramNotification = new TelegramNotification();
    telegramNotification.setName(request.getName());
    telegramNotification.setChatId(request.getChatId());
    telegramNotification.setCreatedBy(userId);

    Boolean result = telegramNotificationMapper.insert(telegramNotification);
    if (result) {
      if (request.getDepartments() != null){
         for (Long department : request.getDepartments()) {
            telegramNotificationMapper.updateDepartment(department, telegramNotification.getId());
         }
      }
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    }
    return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
  }

  @Override
  public ResponseMessage<BaseResult> update(TelegramNotificationUpdateRequest request) {

    Long userId = userService.getUserAuth().getId();
    if (permissionMapper.checkPermission(userId, "Telegram Notification (Edit)") == 0) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
    }

    // Check Duplicate
    if (telegramNotificationMapper.checkDuplicate(request.getChatId(), request.getId()) > 0) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Telegram Chat Id", false));
    }

    // Check Data
    TelegramNotification telegramNotification = new TelegramNotification();
    telegramNotification.setId(request.getId());
    telegramNotification.setName(request.getName());
    telegramNotification.setChatId(request.getChatId());
    telegramNotification.setModifiedBy(userId);
    Boolean result = telegramNotificationMapper.update(telegramNotification);

    if (result) {
      if (request.getDepartments() != null && !request.getDepartments().isEmpty()){
        for (Long department : request.getDepartments()) {
          telegramNotificationMapper.updateDepartment(department, telegramNotification.getId());
        }
      }
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    }
    return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
  }

  @Override
    public ResponseMessage<BaseResult> delete(Long id, Long userId) {

      if (permissionMapper.checkPermission(userId, "Telegram Notification (Delete)") == 0) {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
      }

      Boolean result = this.telegramNotificationMapper.delete(id, userId);
      if (!result) {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
      telegramNotificationMapper.deleteDepartment(id);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    }

  }



