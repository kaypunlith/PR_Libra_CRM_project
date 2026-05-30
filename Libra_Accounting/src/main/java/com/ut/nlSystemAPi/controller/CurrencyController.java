package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.Currency.CurrencyRequest;
import com.ut.nlSystemAPi.model.request.Login.Currency.CurrencyUpdateRequest;
import com.ut.nlSystemAPi.service.ChartOfAccountService;
import com.ut.nlSystemAPi.service.CurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/currency")
@Api(tags = "17. Currency", description = "Currency Resource")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping("/list")
    @ApiOperation(value = "List currency by filter", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return currencyService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find currency by id", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return currencyService.getOne(id, httpServletRequest);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add currency setting", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody CurrencyRequest currencyRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return currencyService.insert(currencyRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/update")
    @ApiOperation(value = "Add currency setting", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody CurrencyUpdateRequest currencyUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return currencyService.update(currencyUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete currency by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return currencyService.delete(id, httpServletRequest);
    }

}
