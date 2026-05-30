package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PurchaseBillFilter;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillUpdateRequest;
import com.ut.nlSystemAPi.service.PurchaseBillService;
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
@RequestMapping("/purchase-bill")
@Api(tags = "31. Purchase Bill", description = "Purchase Bill Resource")
@Timed
public class PurchaseBillController {

    @Autowired
    private PurchaseBillService purchaseBillService;

    @PostMapping("/list")
    @ApiOperation(value = "List purchase bill by filter", notes = "Status: 1 = Issues, 2 = Partial, 3 = Fulfilled; PoType: 1 = Product, 2 = Fixed Asset", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody PurchaseBillFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseBillService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find purchase bill by id", notes = "Status: 1 = Issues, 2 = Partial, 3 = Fulfilled;  Type: 1 = Product, 2 = Service; PoType: 1 = Product, 2 = Fixed Asset",  authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseBillService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new purchase bill", notes = "Type: 1 = Product, 2 = Service; PoType: 1 = Product, 2 = Fixed Asset", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody PurchaseBillRequest purchaseBillRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseBillService.insert(purchaseBillRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update purchase bill by id", notes = "Type: 1 = Product, 2 = Service; PoType: 1 = Product, 2 = Fixed Asset", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody PurchaseBillUpdateRequest purchaseBillUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseBillService.update(purchaseBillUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete purchase bill by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseBillService.delete(id, httpServletRequest);
    }

    @PostMapping("/approve/{id}/{isApprove}")
    @ApiOperation(value = "Approve purchase bill by id",  authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> approve(@PathVariable("id") Long id, @PathVariable("isApprove") Long isApprove, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseBillService.approve(id, isApprove, httpServletRequest);
    }

}
