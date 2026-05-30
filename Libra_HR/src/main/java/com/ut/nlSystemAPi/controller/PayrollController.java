package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.PayrollFilter;
import com.ut.nlSystemAPi.model.PayrollLock;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.OtherPayFilter;
import com.ut.nlSystemAPi.model.filter.ViewOtFilter;
import com.ut.nlSystemAPi.model.request.*;
import com.ut.nlSystemAPi.service.PayrollService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/payroll")
@Api(tags = "Payroll", description = "Payroll Resource")
@Timed
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @PostMapping("/list")
    @ApiOperation(value = "Payroll (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody PayrollFilter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return this.payrollService.getList(filter);
    }
    @PostMapping("/view/other/pay")
    @ApiOperation(value = "Payroll (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListOtherPay(@RequestBody OtherPayFilter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return this.payrollService.getListViewOtherPay(filter);
    }
    @PostMapping("/view/history/bonus")
    @ApiOperation(value = "Payroll (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> ViewHistoryBonus(@RequestBody ViewOtFilter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return this.payrollService.viewHistoryBonus(filter);
    }

    @PostMapping("/view/attendance")
    @ApiOperation(value = "Payroll (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> ViewAttendance(@RequestBody ViewOtFilter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return this.payrollService.viewAttendance(filter);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Payroll by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return payrollService.getOne(id);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Payroll (Add)", notes = "Payroll Detail Status (0: Disapprove, 1: Approve)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody PayrollRequest payrollRequest, BindingResult bindingResult) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return payrollService.insert(payrollRequest);
    }


    @PostMapping(value = "/payroll/send/telegram", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Payroll (Add)", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeesId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "payDate", dataType = "date", paramType = "form", required = true)
    })
    public ResponseMessage<BaseResult> sendTelegram(@ApiIgnore PayrollSendTelegramRequest request, @RequestPart(name = "file", required = false) MultipartFile file, BindingResult bindingResult) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return payrollService.payrollsSendTelegram(request,file);
    }


    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Payroll by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody PayrollRequest payrollRequest, BindingResult bindingResult) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return payrollService.update(payrollRequest);
    }


    @PostMapping(value = "/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Delete Payroll by id ", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "payDate", dataType = "string", paramType = "form", required = true)
    })
    public ResponseMessage<BaseResult> delete(@ApiIgnore PayrollRequest payrollRequest) {
        return payrollService.delete(payrollRequest);
    }

    @PostMapping(value = "/lock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Lock Payroll", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> lock(@RequestBody PayrollLock payrollLock) {
        return payrollService.lock(payrollLock);
    }

}
