package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ExpenseRequestFilter;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.ApproveRequest;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.ExpenseRequestRequest;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.ExpenseRequestUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.CloseRequest;
import com.ut.nlSystemAPi.service.ExpenseRequestService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/expense-request")
@Api(tags = "30. Expense Request", description = "Expense Request Resource")
@Timed
public class ExpenseRequestController {
    
    @Autowired
    private ExpenseRequestService expenseRequestService;

    @PostMapping("/list")
    @ApiOperation(value = "List expense request  type by filter", notes = "List expense request type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody ExpenseRequestFilter expenseRequestFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return expenseRequestService.getList(expenseRequestFilter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find expense request by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, ExpenseRequestFilter expenseRequestFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return expenseRequestService.getOne(id, expenseRequestFilter, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new expense request", notes = "Add new meeting board", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody ExpenseRequestRequest expenseRequestRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return expenseRequestService.insert(expenseRequestRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update expense request by id", notes = "Update meeting board", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody ExpenseRequestUpdateRequest expenseRequestUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return expenseRequestService.update(expenseRequestUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete expense request by id", notes = "Delete shipment", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return expenseRequestService.delete(id, httpServletRequest);
    }

    @PostMapping(value = "/close", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update close request", notes = "close 1=close,0=open ",  authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateIsClose(@RequestBody CloseRequest closeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return expenseRequestService.updateClose(closeRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/approve", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update approve request", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateApprove(@RequestBody ApproveRequest approveRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return expenseRequestService.updateApprove(approveRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/get-list-approve/{id}")
    @ApiOperation(value = "Get list approve", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListApprove(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return expenseRequestService.getListApprove(id, httpServletRequest);
    }

}