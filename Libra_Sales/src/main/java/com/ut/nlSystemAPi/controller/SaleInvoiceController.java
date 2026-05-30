package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.StatusRequest;
import com.ut.nlSystemAPi.model.filter.CreditMemoReceiptFilter;
import com.ut.nlSystemAPi.model.filter.SaleInvoiceFilter;
import com.ut.nlSystemAPi.model.filter.SalesInvoiceReceiptFilter;
import com.ut.nlSystemAPi.model.request.CreditMemo.ApplyWithInvoiceRequest;
import com.ut.nlSystemAPi.model.request.CreditMemo.CreditMemoPayRequest;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SaleInvoiceRequest;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SaleInvoiceUpdateRequest;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SalesInvoicePayRequest;
import com.ut.nlSystemAPi.service.SaleInvoiceService;
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
@RequestMapping("/sales-invoice")
@Api(tags = "22. Sales Invoice", description = "Sales Invoice Resource")
@Timed
public class SaleInvoiceController {

    @Autowired
    private SaleInvoiceService saleInvoiceService;

    @PostMapping("/list")
    @ApiOperation(value = "List sales order by filter", notes = "Type: 1: Trading, 2: Software", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody SaleInvoiceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find sales order by id", notes = "Type: 1: Product, 2: Service, 3: Misc (Details)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.getOne(id, httpServletRequest);
    }

    @PostMapping("/find-receipt/{code}")
    @ApiOperation(value = "Find sales order by code", notes = "Type: 1: Product, 2: Service, 3: Misc (Details)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findByCode(@PathVariable("code") String code, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.getOneByCode(code, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new sales order", notes = "Type: 1: Trading, 2: Software; Type: 1: Product, 2: Service, 3: Misc (Details)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody SaleInvoiceRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update sales order by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody SaleInvoiceUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.update(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete sales order by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.delete(id, httpServletRequest);
    }

    @PostMapping("/close/{id}")
    @ApiOperation(value = "Delete sales order by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> close(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.close(id, httpServletRequest);
    }

    @PostMapping("/close-recurrence/{id}")
    @ApiOperation(value = "Close sales order recurrence by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> closeRecurrence(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.closeRecurrence(id, httpServletRequest);
    }

    @PostMapping("/approve")
    @ApiOperation(value = "Approve sales order by id", notes = "Status: 1: Disapproved, 2: Approved", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> approve(@RequestBody StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.approve(request, httpServletRequest);
    }

    @PostMapping("/pay")
    @ApiOperation(value = "Pay credit memo", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> pay(@RequestBody SalesInvoicePayRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.pay(request, httpServletRequest);
    }

    @PostMapping("/list-receipt")
    @ApiOperation(value = "List credit memo receipt", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listReceipt(@RequestBody SalesInvoiceReceiptFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.listReceipt(filter, httpServletRequest);
    }

    @PostMapping("/void-receipt")
    @ApiOperation(value = "Void credit memo receipt", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> voidReceipt(@RequestBody SalesInvoiceReceiptFilter request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleInvoiceService.voidReceipt(request, httpServletRequest);
    }

}
