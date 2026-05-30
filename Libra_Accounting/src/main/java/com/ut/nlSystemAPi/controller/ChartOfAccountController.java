package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountUpdateRequest;
import com.ut.nlSystemAPi.service.ChartOfAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/chart-of-account")
@Api(tags = "11. ChartOfAccount", description = "Chart Of Account Resource")
public class ChartOfAccountController {

    @Autowired
    private ChartOfAccountService chartOfAccountService;

    @PostMapping("/list")
    @ApiOperation(value = "List chart of account by filter", notes = "StatusId (0: Disapprove, 1: Active, 2: Deleted);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listChartOfAccount(@RequestBody ChartAccountFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return chartOfAccountService.getListChartOfAccount(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find chart of account by id", notes = "StatusId (0: Disapprove, 1: Active, 2: Deleted);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return chartOfAccountService.getOne(id, httpServletRequest);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add chart of account", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody ChartOfAccountRequest chartOfAccountRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return chartOfAccountService.insert(chartOfAccountRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/update")
    @ApiOperation(value = "Add chart of account", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody ChartOfAccountUpdateRequest chartOfAccountUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return chartOfAccountService.update(chartOfAccountUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete chart of account by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return chartOfAccountService.delete(id, httpServletRequest);
    }

    @PostMapping("/update-status/{id}/{statusId}")
    @ApiOperation(value = "Delete chart of account by id", notes = "StatusId (0: Disapprove, 1: Active, 2: Deleted);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateStatus(@PathVariable("id") Long id, @PathVariable("statusId") Long statusId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return chartOfAccountService.updateStatus(id, statusId, httpServletRequest);
    }

    @PostMapping("/list-data")
    @ApiOperation(value = "List chart of account by filter", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listBySelect(@RequestBody ChartAccountFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return chartOfAccountService.listBySelect(filter, httpServletRequest);
    }
}
