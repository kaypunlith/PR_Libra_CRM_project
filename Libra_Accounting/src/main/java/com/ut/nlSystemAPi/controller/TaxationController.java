package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TaxationFilter;
import com.ut.nlSystemAPi.model.request.Login.Taxation.TaxationExchangeRateRequest;
import com.ut.nlSystemAPi.model.request.Login.Taxation.TaxationRequest;
import com.ut.nlSystemAPi.model.request.Login.Taxation.TaxationUpdateRequest;
import com.ut.nlSystemAPi.service.TaxationService;
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
@RequestMapping("/taxation")
@Api(tags = "18. Taxation", description = "Taxation Resource")
public class TaxationController {

    @Autowired
    private TaxationService taxationService;

    @PostMapping("/list")
    @ApiOperation(value = "List taxation by filter", notes = "typeId = 1: Sales; 2: Purchase", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> list(@RequestBody TaxationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return taxationService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find taxation by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return taxationService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new taxation", notes = "typeId = 1: Sales; 2: Purchase", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody TaxationRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return taxationService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update taxation", notes = "typeId = 1: Sales; 2: Purchase", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody TaxationUpdateRequest requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return taxationService.update(requestUpdate, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete taxation by id", notes = "Delete taxation by id", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return taxationService.delete(id, httpServletRequest);
    }

    @PostMapping(value = "/update-exchange-rate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update tax exchange rate amount", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateExchangeRate(@RequestBody TaxationExchangeRateRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return taxationService.updateExchangeRate(request, httpServletRequest);
    }

}
