package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Delivery.DeliveryRequest;
import com.ut.nlSystemAPi.model.filter.SaleInvoiceFilter;
import com.ut.nlSystemAPi.service.DeliveryService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/delivery-note")
@Api(tags = "39. Delivery Note", description = "Delivery Note Resource")
@Timed
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/list")
    @ApiOperation(value = "List delivery by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return deliveryService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find delivery by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return deliveryService.getOne(id, httpServletRequest);
    }

    @PostMapping("/list-invoice-product")
    @ApiOperation(value = "List sales invoice with details for delivery", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listInvoiceProduct(@RequestBody SaleInvoiceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return deliveryService.listInvoiceProduct(filter, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new delivery", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody DeliveryRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return deliveryService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/pick/{id}")
    @ApiOperation(value = "Pick delivery", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> pick(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return deliveryService.pick(id, null, httpServletRequest);
    }

    @PostMapping("/undo/{id}")
    @ApiOperation(value = "Undo delivery", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> undo(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return deliveryService.undo(id, httpServletRequest);
    }

    @PostMapping("/approve/{id}")
    @ApiOperation(value = "Approve delivery", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> approve(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return deliveryService.approve(id, httpServletRequest);
    }
}
