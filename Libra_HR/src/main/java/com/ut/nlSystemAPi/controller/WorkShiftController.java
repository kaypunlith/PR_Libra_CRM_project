package com.ut.nlSystemAPi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.WorkShiftRequest;
import com.ut.nlSystemAPi.model.request.WorkShiftUpdateRequest;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.service.WorkShiftService;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/work-shift")
@Api(tags = "Work Shift")
@Timed
public class WorkShiftController {

  @Autowired
  private WorkShiftService workShiftService;

  @Autowired
  private UserService userService;

  @PostMapping("/list")
  @ApiOperation(value = "Work shift (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> getList(@RequestBody Filter filter, HttpServletRequest httpServletRequest)
      throws UnknownHostException {
    // Check Header Token
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return this.workShiftService.getList(filter, httpServletRequest);
  }

  @PostMapping("/find/{id}")
  @ApiOperation(value = "Find work shift by ID", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
    // Check Header Token
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return this.workShiftService.getOne(id);
  }

  @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Work shift (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> add(@RequestBody WorkShiftRequest workShiftRequest, BindingResult bindingResult) throws UnknownHostException {
    Long userId = this.userService.getUserAuth().getId();
    // Check Header Token
    if (userId == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    // Check Validation
    if (bindingResult.hasErrors()) {
      return ResponseMessageUtils.makeResponse(false, bindingResult);
    }
    return this.workShiftService.insert(workShiftRequest, userId, null);
  }

  @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Work shift (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> update(@RequestBody WorkShiftUpdateRequest workShiftUpdateRequest,
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
    return this.workShiftService.update(workShiftUpdateRequest, userId);
  }

  @PostMapping("/delete/{id}")
  @ApiOperation(value = "Delete Work Shift by id", notes = "Delete document Work shift", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
    Long userId = this.userService.getUserAuth().getId();
    // Check Header Token
    if (userId == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return this.workShiftService.delete(id, userId);
  }

  @PostMapping("/list-without-permission")
  @ApiOperation(value = "Work shift (List without permission)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> getListDropDown(@RequestBody Filter filter, HttpServletRequest httpServletRequest)
          throws UnknownHostException {
    // Check Header Token
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return this.workShiftService.getListDropDown(filter, httpServletRequest);
  }

  @PostMapping("/list-work-shift-type")
  @ApiOperation(value = "Work shift type", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> getListWorkShiftType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    // Check Header Token
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return this.workShiftService.getListWorkShiftType(filter, httpServletRequest);
  }


}
