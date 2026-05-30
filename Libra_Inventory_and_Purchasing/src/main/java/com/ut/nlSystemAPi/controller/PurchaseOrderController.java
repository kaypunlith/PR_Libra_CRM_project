package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PurchaseOrderFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.PurchaseOrder.PurchaseOrderRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseOrder.PurchaseOrderUpdateRequest;
import com.ut.nlSystemAPi.service.PurchaseOrderService;
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
@RequestMapping("/purchase-order")
@Api(tags = "28. Purchase Order", description = "Purchase Order Resource")
@Timed
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/list")
    @ApiOperation(value = "List purchase order by filter", notes = "Status: 1 = Issues, 2 = Partial, 3 = Fulfilled;  isClose: 1 = Close, 0 = Open", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody PurchaseOrderFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseOrderService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find purchase order by id", notes = "Status: 1 = Issues, 2 = Partial, 3 = Fulfilled;  isClose: 1 = Close, 0 = Open; Type: 1 = Product, 2 = Service",authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseOrderService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new purchase order", notes = "Status: 1 = Issues, 2 = Partial, 3 = Fulfilled;  isClose: 1 = Close, 0 = Open; Type: 1 = Product, 2 = Service", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody PurchaseOrderRequest purchaseOrderRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseOrderService.insert(purchaseOrderRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update purchase order by id", notes = "Status: 1 = Issues, 2 = Partial, 3 = Fulfilled;  isClose: 1 = Close, 0 = Open; Type: 1 = Product, 2 = Service", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody PurchaseOrderUpdateRequest purchaseOrderUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseOrderService.update(purchaseOrderUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete purchase order by id",  authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseOrderService.delete(id, httpServletRequest);
    }

    @PostMapping("/close-status")
    @ApiOperation(value = "Close status by id", notes = "isClose: 1 = Close, 0 = Open", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> closeStatus(@RequestBody StatusFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseOrderService.closeStatus(filter, httpServletRequest);
    }

}