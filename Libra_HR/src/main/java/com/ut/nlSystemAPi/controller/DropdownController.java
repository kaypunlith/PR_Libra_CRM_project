package com.ut.nlSystemAPi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.DropdownFilter;
import com.ut.nlSystemAPi.service.DropdownService;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/dropdown")
@Api(tags = "Dropdown")
@Timed
public class DropdownController {

  @Autowired
  private DropdownService dropdownService;

  @PostMapping("/group")
  @ApiOperation(value = "List day dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listGroup(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getGroupDropdown(filter);
  }

  @PostMapping("/department")
  @ApiOperation(value = "List day dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
          @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listDepartment(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getDepartmentDropdown(filter);
  }

  @PostMapping("/day")
  @ApiOperation(value = "List day dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listDay(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getDayDropdown(filter);
  }

  @PostMapping("/leave-request-type")
  @ApiOperation(value = "List Leave Request Type dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listLeaveRequestType(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getLeaveRequestTypeDropdown(filter);
  }

  @PostMapping("/work-shift")
  @ApiOperation(value = "List work shift dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listWorkShift(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getWorkShiftDropdown(filter);
  }

  @PostMapping("/position")
  @ApiOperation(value = "List position dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listPosition(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getPositionDropdown(filter);
  }

  @PostMapping("/employee-type")
  @ApiOperation(value = "List employee type dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listEmployeeType(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getEmployeeTypeDropdown(filter);
  }

  @PostMapping("/employee-status")
  @ApiOperation(value = "List employee status dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listEmployeeStatus(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getEmployeeStatusDropdown(filter);
  }

  @PostMapping("/marital-status")
  @ApiOperation(value = "List marital status dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listMaritalStatus(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getMaritalStatusDropdown(filter);
  }

  @PostMapping("/mission-mean")
  @ApiOperation(value = "List mission mean dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listMissionMean(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getMissionMeanDropdown(filter);
  }

  @PostMapping("/mission-destination")
  @ApiOperation(value = "List mission mean dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listMissionDestination(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getMissionDestinationDropdown(filter);
  }

  @PostMapping("/mission-organization")
  @ApiOperation(value = "List mission organization dropdown by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {
      @Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> listMissionOrganization(@RequestBody DropdownFilter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return dropdownService.getMissionOrganizationDropdown(filter);
  }

}
