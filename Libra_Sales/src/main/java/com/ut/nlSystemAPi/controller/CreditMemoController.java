package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.CreditMemoReceiptFilter;
import com.ut.nlSystemAPi.model.filter.CreditMemoFilter;
import com.ut.nlSystemAPi.model.request.CreditMemo.ApplyWithInvoiceRequest;
import com.ut.nlSystemAPi.model.request.CreditMemo.CreditMemoPayRequest;
import com.ut.nlSystemAPi.model.request.CreditMemo.CreditMemoRequest;
import com.ut.nlSystemAPi.model.request.CreditMemo.CreditMemoUpdateRequest;
import com.ut.nlSystemAPi.service.CreditMemoService;
import io.micrometer.core.annotation.Timed;
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
@RequestMapping("/credit-memo")
@Api(tags = "25. Credit Memo", description = "Credit Memo Resource")
@Timed
public class CreditMemoController {

    @Autowired
    private CreditMemoService creditMemoService;

    @PostMapping("/list")
    @ApiOperation(value = "List credit memo by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody CreditMemoFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find credit memo by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new credit memo", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody CreditMemoRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update credit memo by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody CreditMemoUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.update(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete credit memo by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.delete(id, httpServletRequest);
    }

    @PostMapping("/pick/{id}")
    @ApiOperation(value = "Receive credit memo by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> pick(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.receive(id, httpServletRequest);
    }

    @PostMapping("/pay")
    @ApiOperation(value = "Pay credit memo", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> pay(@RequestBody CreditMemoPayRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.pay(request, httpServletRequest);
    }

    @PostMapping("/apply-with-invoice")
    @ApiOperation(value = "Apply credit memo with sales invoice", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> applyInvoice(@RequestBody ApplyWithInvoiceRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.applyWithInvoice(request, httpServletRequest);
    }

    @PostMapping("/list-receipt")
    @ApiOperation(value = "List credit memo receipt", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listReceipt(@RequestBody CreditMemoReceiptFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.listReceipt(filter, httpServletRequest);
    }

    @PostMapping("/void-receipt")
    @ApiOperation(value = "Void credit memo receipt", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> voidReceipt(@RequestBody CreditMemoReceiptFilter request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return creditMemoService.voidReceipt(request, httpServletRequest);
    }
}