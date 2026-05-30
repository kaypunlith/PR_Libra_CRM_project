package com.ut.nlSystemAPi.controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeUpdateStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.http.MediaType;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeUpdateRequest;
import com.ut.nlSystemAPi.service.ChartAccountTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/chart-account-type")
@Api(tags = "12. Chart of Account Type", description = "Chart of Account Type")
public class ChartAccountTypeController {

    @Autowired
    private ChartAccountTypeService accountTypeService;

    @PostMapping("/list")
    @ApiOperation(value = "List chart of account type by filter", notes = "StatusId (0: Disapprove, 1: Active, 2: Deleted);", authorizations = {
            @Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> list(@RequestBody Filter filter, HttpServletRequest httpServletRequest)
            throws UnknownHostException {

        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountTypeService.getList(filter, httpServletRequest);
    }

    @PostMapping(value = "/update-status", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update status chart of account type by id", notes = "StatusId (0: Disapprove, 1: Active, 2: Deleted);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateStatus(@RequestBody ChartAccountTypeUpdateStatusRequest chartAccountTypeUpdateStatusRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountTypeService.updateStatus(chartAccountTypeUpdateStatusRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find chart of account type by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountTypeService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new chart of account type", notes = "Add new chart of account type", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody ChartAccountTypeRequest chartAccountTypeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountTypeService.insert(chartAccountTypeRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update chart of account type", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody ChartAccountTypeUpdateRequest chartAccountTypeUpdateRequest,
            BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountTypeService.update(chartAccountTypeUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete chart of account type by id", notes = "Delete chart of account type by id", authorizations = {
            @Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest)
            throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountTypeService.delete(id, httpServletRequest);
    }

}
