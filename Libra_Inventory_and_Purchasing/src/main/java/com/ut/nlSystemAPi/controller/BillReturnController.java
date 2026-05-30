package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.BillReturnFilter;
import com.ut.nlSystemAPi.model.filter.BillReturnReceiptFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.BillReturn.BillReturnRequest;
import com.ut.nlSystemAPi.model.request.Login.BillReturn.BillReturnUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.BillReturn.PayBillReturnRequest;
import com.ut.nlSystemAPi.model.request.Login.BillReturn.PayBillReturnWithPbsRequest;
import com.ut.nlSystemAPi.service.BillReturnService;
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
@RequestMapping("/bill-return")
@Api(tags = "35. Bill Return", description = "Bill Return Resource")
@Timed
public class BillReturnController {

    @Autowired
    private BillReturnService billReturnService;

    @PostMapping("/list")
    @ApiOperation(value = "List bill return by filter", notes = "List role by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody BillReturnFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find bill return by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new bill return", notes = "Add new purchase order", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody BillReturnRequest billReturnRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.insert(billReturnRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update bill return by id", notes = "Update role", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody BillReturnUpdateRequest billReturnUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.update(billReturnUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete bill return by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.delete(id, httpServletRequest);
    }

    @PostMapping("/pick/{id}")
    @ApiOperation(value = "Pick bill return by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> pick(@RequestBody StatusFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.pick(filter, httpServletRequest);
    }

    @PostMapping("/pay")
    @ApiOperation(value = "Pay bill return", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> pay(@RequestBody PayBillReturnRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.pay(request, httpServletRequest);
    }

    @PostMapping("/apply-with-pb")
    @ApiOperation(value = "Pay bill return with purchase bill", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> applyPb(@RequestBody PayBillReturnWithPbsRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.applyPb(request, httpServletRequest);
    }

    @PostMapping("/list-receipt")
    @ApiOperation(value = "List bill return receipt", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listReceipt(@RequestBody BillReturnReceiptFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.listReceipt(filter, httpServletRequest);
    }

    @PostMapping("/void-receipt/{id}")
    @ApiOperation(value = "Void bill return receipt by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> voidReceipt(@RequestBody BillReturnReceiptFilter request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return billReturnService.voidReceipt(request, httpServletRequest);
    }

}