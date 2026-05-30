package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.TelegramNotificationRequest;
import com.ut.nlSystemAPi.model.request.TelegramNotificationUpdateRequest;
import com.ut.nlSystemAPi.service.TelegramNotificationService;
import com.ut.nlSystemAPi.service.UserService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/telegram-notification")
@Api(tags = "Telegram Notification")
@Timed
public class TelegramNotificationController {

  @Autowired
  private TelegramNotificationService telegramNotificationService;

  @Autowired
  private UserService userService;

  @PostMapping("/list")
  @ApiOperation(value = "Telegram Notification (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> getList(@RequestBody Filter filter) {
    // Check Header Token
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return this.telegramNotificationService.getList(filter);
  }

  @PostMapping("/find/{id}")
  @ApiOperation(value = "Telegram Notification by ID", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
    // Check Header Token
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return this.telegramNotificationService.getOne(id);
  }

  @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Telegram Notification (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> add(@RequestBody TelegramNotificationRequest telegramNotificationRequest, BindingResult bindingResult) {
    Long userId = this.userService.getUserAuth().getId();
    // Check Header Token
    if (userId == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    // Check Validation
    if (bindingResult.hasErrors()) {
      return ResponseMessageUtils.makeResponse(false, bindingResult);
    }
    return this.telegramNotificationService.insert(telegramNotificationRequest, userId);
  }

  @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Telegram Notification (Updated)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> update(@RequestBody TelegramNotificationUpdateRequest telegramNotificationUpdateRequest,
      BindingResult bindingResult) {
     Long userId = this.userService.getUserAuth().getId();
    // Check Header Token
    if (userId == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    // Check Validation
    if (bindingResult.hasErrors()) {
      return ResponseMessageUtils.makeResponse(false, bindingResult);
    }
    return this.telegramNotificationService.update(telegramNotificationUpdateRequest);
  }

  @PostMapping("/delete/{id}")
  @ApiOperation(value = "Telegram notification by id", notes = "Delete document Work shift", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
    Long userId = this.userService.getUserAuth().getId();
    // Check Header Token
    if (userId == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return this.telegramNotificationService.delete(id, userId);
  }

}
