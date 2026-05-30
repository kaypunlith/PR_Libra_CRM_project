package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.StatusRequest;
import com.ut.nlSystemAPi.model.filter.SaleOrderFilter;
import com.ut.nlSystemAPi.model.request.SaleOrder.SaleOrderRequest;
import com.ut.nlSystemAPi.model.request.SaleOrder.SaleOrderUpdateRequest;
import com.ut.nlSystemAPi.service.SaleOrderService;
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
@RequestMapping("/sales-order")
@Api(tags = "21. Sales Order", description = "Sales Order Resource")
@Timed
public class SaleOrderController {

    @Autowired
    private SaleOrderService saleOrderService;

    @PostMapping("/list")
    @ApiOperation(value = "List sales order by filter", notes = "Type: 1: Trading, 2: Software", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody SaleOrderFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleOrderService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find sales order by id", notes = "Type: 1: Product, 2: Service, 3: Misc (Details)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleOrderService.getOne(id, null, httpServletRequest);
    }

    @PostMapping("/find/{id}/{warehouseId}")
    @ApiOperation(value = "Find sales order by id", notes = "Type: 1: Product, 2: Service, 3: Misc (Details)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findWithStockCheck(@PathVariable("id") Long id, @PathVariable("warehouseId") Long warehouseId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleOrderService.getOne(id, warehouseId, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new sales order", notes = "Type: 1: Trading, 2: Software; Type: 1: Product, 2: Service, 3: Misc (Details)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody SaleOrderRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleOrderService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update sales order by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody SaleOrderUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleOrderService.update(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete sales order by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleOrderService.delete(id, httpServletRequest);
    }

    @PostMapping("/approve")
    @ApiOperation(value = "Approve sales order by id", notes = "Status: 1: Disapproved, 2: Approved", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> approve(@RequestBody StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleOrderService.approve(request, httpServletRequest);
    }

    @PostMapping("/close")
    @ApiOperation(value = "Close sales order by id", notes = "Status: 1: Close, 0: Open", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> close(@RequestBody StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return saleOrderService.close(request, httpServletRequest);
    }


}