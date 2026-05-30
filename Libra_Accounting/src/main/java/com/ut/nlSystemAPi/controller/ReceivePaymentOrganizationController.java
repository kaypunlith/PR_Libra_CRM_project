package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentOrganizationFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentPrintFilter;
import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentOrganization.ReceivePaymentOrganizationUpdateRequest;
import com.ut.nlSystemAPi.service.ReceivePaymentOrganizationService;
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
@RequestMapping("/receive-payment-organization")
@Api(tags = "24. Receive Payment Organization", description = "Receive Payment Organization Resource")
public class ReceivePaymentOrganizationController {

    @Autowired
    private ReceivePaymentOrganizationService receivePaymentOrganizationService;

    @PostMapping("/list")
    @ApiOperation(value = "List Receive Payment Organization by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody ReceivePaymentOrganizationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return receivePaymentOrganizationService.getList(filter, httpServletRequest);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save Receive Payment Organization", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> save(@RequestBody ReceivePaymentOrganizationUpdateRequest requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return receivePaymentOrganizationService.save(requestUpdate, bindingResult, httpServletRequest);
    }

    @PostMapping("/list-print")
    @ApiOperation(value = "List Receive Payment Print by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListPrint(@RequestBody ReceivePaymentPrintFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return receivePaymentOrganizationService.getListPrint(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Receive Payment Print by id", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return receivePaymentOrganizationService.getOne(id, httpServletRequest);
    }

}
