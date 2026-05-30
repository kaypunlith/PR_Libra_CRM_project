package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.filter.ChartAccountNetIncomeFilter;
import com.ut.nlSystemAPi.model.request.Login.AccountClosingDate.AccountClosingDateSettingRequest;
import com.ut.nlSystemAPi.service.AccountClosingDateSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/account-closing-date-setting")
@Api(tags = "33. Account Closing Date Setting", description = "Account Closing Date Setting Resource")
public class AccountClosingDateSettingController {

    @Autowired
    private AccountClosingDateSettingService accountClosingDateSettingService;

    @PostMapping("/list")
    @ApiOperation(value = "List Account Closing Date Setting by filter", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listChartOfAccount(@RequestBody ChartAccountFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountClosingDateSettingService.getListChartOfAccount(filter, httpServletRequest);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add Account Closing Date Setting", notes = "accountType = 1: Retained Earnings Account, 2: Income Summary Account", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody AccountClosingDateSettingRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountClosingDateSettingService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/NetIncome")
    @ApiOperation(value = "List Account Closing Date Setting by filter", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getNetIncome(@RequestBody ChartAccountNetIncomeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountClosingDateSettingService.getNetIncome(filter, httpServletRequest);
    }
}
