package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentPrintFilter;
import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePayment.ReceivePaymentUpdateRequest;
import com.ut.nlSystemAPi.service.ReceivePaymentService;
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
@RequestMapping("/receive-payment")
@Api(tags = "23. Receive Payment", description = "Receive Payment Resource")
public class ReceivePaymentController {

    @Autowired
    private ReceivePaymentService receivePaymentService;

    @PostMapping("/list")
    @ApiOperation(value = "List Receive Payment by filter", notes = "Type: 1=VAT, 2=None VAT;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody ReceivePaymentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return receivePaymentService.getList(filter, httpServletRequest);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save Receive Payment", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> save(@RequestBody ReceivePaymentUpdateRequest requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return receivePaymentService.save(requestUpdate, bindingResult, httpServletRequest);
    }

    @PostMapping("/list-print")
    @ApiOperation(value = "List Receive Payment Print by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListPrint(@RequestBody ReceivePaymentPrintFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return receivePaymentService.getListPrint(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Receive Payment Print by id", notes = "StatusId (1: Active, 0: Inactive); isVat: 1=Ture, 0:False;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return receivePaymentService.getOne(id, httpServletRequest);
    }

    @PostMapping("/print-credit-statement")
    @ApiOperation(value = "List Receive Payment credit statement by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getCreditStatement(@RequestBody ReceivePaymentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return receivePaymentService.getCreditStatement(filter, httpServletRequest);
    }
}
