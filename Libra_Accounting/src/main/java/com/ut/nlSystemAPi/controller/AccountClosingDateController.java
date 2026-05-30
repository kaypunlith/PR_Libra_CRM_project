package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.AccountClosingDateRequest;
import com.ut.nlSystemAPi.service.AccountClosingDateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/account-closing-date")
@Api(tags = "16. Account Closing Date", description = "Account Closing Date Resource")
public class AccountClosingDateController {

    @Autowired
    private AccountClosingDateService accountClosingDateService;

    @PostMapping("/list")
    @ApiOperation(value = "List account closing date by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountClosingDateService.getList(httpServletRequest);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "Add account closing date", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> insert(@RequestBody AccountClosingDateRequest accountClosingDateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return accountClosingDateService.add(accountClosingDateRequest, bindingResult, httpServletRequest);
    }

}
