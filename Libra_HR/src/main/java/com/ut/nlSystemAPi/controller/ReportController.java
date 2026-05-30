package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.EmployeeFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LeaveReportFilter;
import com.ut.nlSystemAPi.model.filter.ReportAttendanceFilter;
import com.ut.nlSystemAPi.model.response.DepositReportFilter;
import com.ut.nlSystemAPi.model.response.DepositReportMonthlyFilter;
import com.ut.nlSystemAPi.model.response.ProFundReportFilter;
import com.ut.nlSystemAPi.service.ReportService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hr-report")
@Api(tags = "Report")
@Timed
@SuppressWarnings("all")
public class ReportController {

  @Autowired
  private ReportService reportService;

  @PostMapping("/annual-leave")
  @ApiOperation(value = "Annual Leave Report", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> getAnnualLeave(@RequestBody LeaveReportFilter filter) {
    // Check Header Token
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return reportService.getAnnualLeave(filter);
  }

  @PostMapping("/staff-profile")
  @ApiOperation(value = "Staff Profile Report", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> getReportStaffProfile(@RequestBody EmployeeFilter filter) {
    // Check Header Token
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return reportService.getReportStaffProfile(filter);
  }

  @PostMapping("/staff-profile/find/{id}")
  @ApiOperation(value = "Staff Profile Find by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> getReportStaffProfileFind(@PathVariable("id") Long id) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return reportService.getReportStaffProfileFind(id);
  }

  @PostMapping("/staff-loan")
  @ApiOperation(value = "Staff Loan Report", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> getStaffLoan(@RequestBody Filter filter) {
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return reportService.getStaffLoan(filter);
  }

//  @PostMapping("/deposit")
//  @ApiOperation(value = "Deposit Request Report", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
//  public ResponseMessage<BaseResult> getDeposit(@RequestBody DepositReportFilter filter) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null) {
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return reportService.getDeposit(filter);
//  }

//  @PostMapping("/with-draw")
//  @ApiOperation(value = "Deposit Request Report", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
//  public ResponseMessage<BaseResult> getDepositRequest(@RequestBody DepositReportFilter filter) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null) {
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return reportService.getDepositRequest(filter);
//  }
//
//  @PostMapping("/providentFund-report")
//  @ApiOperation(value = "Provident Fund Report", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
//  public ResponseMessage<BaseResult> getProvidentFund(@RequestBody ProFundReportFilter filter) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null) {
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return reportService.getProvidentFund(filter);
//  }
//
//  @PostMapping("/yearly-deposit")
//  @ApiOperation(value = "Report yearly deposit list", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer") })
//  public ResponseMessage<BaseResult> getMonthlyDeposit(@RequestBody DepositReportMonthlyFilter filter) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null) {
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return reportService.getYearlyDeposit(filter);
//  }

  @PostMapping("/attendance")
  @ApiOperation(value = "Report attendance list", notes = "<li>statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success</li> <br><li>timeClock: 1(Check In), 2(Check Out)</li> <br><li>status: 1(On-Time), 2(Late), 3(Early)</li>", authorizations = {@Authorization(value = "Bearer") })
  public ResponseMessage<BaseResult> getAttendances(@RequestBody ReportAttendanceFilter filter) {
    // Check Header Token
    if (UserAuthSession.getUserAuth() == null) {
      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    }
    return this.reportService.getAttendance(filter);
  }

}
