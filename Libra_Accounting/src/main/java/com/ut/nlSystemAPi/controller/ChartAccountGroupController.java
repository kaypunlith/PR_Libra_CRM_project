package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.AccountTypeFilter;
import com.ut.nlSystemAPi.model.filter.ChartAccountGroupFilter;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup.ChartAccountGroupRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup.ChartAccountGroupUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup.ChartAccountGroupUpdateStatusRequest;
import com.ut.nlSystemAPi.service.ChartAccountGroupService;
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
@RequestMapping("/chart-account-group")

@Api(tags = "13. Chart of Account Group", description = "Chart of Account Group Resource")
public class ChartAccountGroupController {

    @Autowired
    private ChartAccountGroupService accountGroupService;

    @PostMapping("/list")
    @ApiOperation(value = "List chart of account group by filter", notes = "status: 1, Approve; 0: disapprove", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> list(@RequestBody ChartAccountGroupFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {

        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountGroupService.getList(filter, httpServletRequest);
    }

    @PostMapping(value = "/update-status", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update status chart of account group by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateStatus(@RequestBody ChartAccountGroupUpdateStatusRequest updateStatusRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountGroupService.updateStatus(updateStatusRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find chart of account group by id", notes ="expense 0: Expense, 1: Depreciation Expense, 2: Interest Expense, 3: Tax Expenes", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountGroupService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new chart of account group", notes = "Add new chart of account group", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody ChartAccountGroupRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountGroupService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update chart of account group", notes = "expanse: 1, Expense; 2, Depreciation expense; 3, Interest Expense; 4, Tax Expense", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody ChartAccountGroupUpdateRequest requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountGroupService.update(requestUpdate, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete chart of account group by id", notes = "Delete chart of account group by id", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountGroupService.delete(id, httpServletRequest);
    }

}
