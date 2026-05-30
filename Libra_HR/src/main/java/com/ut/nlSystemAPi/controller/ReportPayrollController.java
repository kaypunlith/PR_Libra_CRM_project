package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.response.PayrollReportFilter;
import com.ut.nlSystemAPi.service.ReportPayrollService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll-report")
@Api(tags = "Payroll Report")
@Timed
public class ReportPayrollController {

  @Autowired
  private ReportPayrollService reportPayrollService;

//    @PostMapping("/summary")
//    @ApiOperation(value = "Payroll Summary Report (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//    public ResponseMessage<BaseResult> getListSummary(@RequestBody PayrollReportFilter filter) {
//        // Check Header Token
//        if (UserAuthSession.getUserAuth() == null){
//            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//        }
//        return reportPayrollService.getListSummary(filter);
//    }
//
//    @PostMapping("/bank-paid")
//    @ApiOperation(value = "Payroll bank paid Report (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//    public ResponseMessage<BaseResult> getBankPaid(@RequestBody PayrollReportFilter filter) {
//        // Check Header Token
//        if (UserAuthSession.getUserAuth() == null){
//            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//        }
//        return reportPayrollService.getBankPaid(filter);
//    }
//
//    @PostMapping("/manual-paid")
//    @ApiOperation(value = "Payroll manual paid Report (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//    public ResponseMessage<BaseResult> getManualPaid(@RequestBody PayrollReportFilter filter) {
//        // Check Header Token
//        if (UserAuthSession.getUserAuth() == null){
//            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//        }
//        return reportPayrollService.getManualPaid(filter);
//    }
//
//    @PostMapping("/group-payroll")
//    @ApiOperation(value = "Group Payroll Report (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//    public ResponseMessage<BaseResult> getGroupPayroll(@RequestBody PayrollReportFilter filter) {
//        // Check Header Token
//        if (UserAuthSession.getUserAuth() == null){
//            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//        }
//        return reportPayrollService.getGroupPayroll(filter);
//    }

    @PostMapping("/payroll-balance")
    @ApiOperation(value = "Payroll Balance Report (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getPayrollBalance(@RequestBody PayrollReportFilter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportPayrollService.getPayrollBalance(filter);
    }

//    @PostMapping("/total-manual-paid")
//    @ApiOperation(value = "Total Report Payroll Manual Paid(List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//    public ResponseMessage<BaseResult> getTotalManualPaid(@RequestBody PayrollReportFilter filter) {
//        // Check Header Token
//        if (UserAuthSession.getUserAuth() == null){
//            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//        }
//        return reportPayrollService.getTotalManualPaid(filter);
//    }
//
//
//    @PostMapping("/pay-slip")
//    @ApiOperation(value = " Payroll slip ", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//    public ResponseMessage<BaseResult> getPayslip(@RequestBody PayrollReportFilter filter) {
//        // Check Header Token
//        if (UserAuthSession.getUserAuth() == null){
//            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//        }
//        return reportPayrollService.getPayslip(filter);
//    }


}
