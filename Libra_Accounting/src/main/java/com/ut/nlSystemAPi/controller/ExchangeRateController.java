package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ExchangeRateFilter;
import com.ut.nlSystemAPi.model.request.Login.ExchangeRate.ExchangeRateUpdateRequest;
import com.ut.nlSystemAPi.service.ExchangeRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/exchange-rate")
@Api(tags = "20. Exchange Rate Currency", description = "Exchange Rate Resource")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @PostMapping("/list")
    @ApiOperation(value = "List exchange rate by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody ExchangeRateFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return exchangeRateService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find")
    @ApiOperation(value = "Find exchange rate by id", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@RequestBody ExchangeRateFilter exchangeRateFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return exchangeRateService.getOne(exchangeRateFilter, httpServletRequest);
    }

    @PostMapping("/update")
    @ApiOperation(value = "Add exchange rate setting", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody ExchangeRateUpdateRequest exchangeRateUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return exchangeRateService.update(exchangeRateUpdateRequest, bindingResult, httpServletRequest);
    }

}
