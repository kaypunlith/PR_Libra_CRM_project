package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.BaseFilter;
import com.ut.nlSystemAPi.model.filter.InventoryAdjustmentFilter;
import com.ut.nlSystemAPi.model.filter.QtyOnHandFilter;
import com.ut.nlSystemAPi.model.request.Login.InventoryAdjustment.InventoryAdjustmentRequest;
import com.ut.nlSystemAPi.model.request.Login.InventoryAdjustment.InventoryAdjustmentUpdateRequest;
import com.ut.nlSystemAPi.service.InventoryAdjustmentService;
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
@RequestMapping("/inventory-adjustment")
@Api(tags = "22. Inventory Adjustment", description = "Inventory Adjustment Resource")
@Timed
public class InventoryAdjustmentController {

    @Autowired
    private InventoryAdjustmentService inventoryAdjustmentService;

    @PostMapping("/list")
    @ApiOperation(value = "List inventory adjustment by filter", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody InventoryAdjustmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryAdjustmentService.getList(filter, httpServletRequest);
    }

    @PostMapping("/list-product-in-stock/{warehouseId}")
    @ApiOperation(value = "List product with stock by warehouse", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProductInStock(@PathVariable("warehouseId") Long warehouseId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryAdjustmentService.listProductInStock(warehouseId, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find inventory adjustment by id", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> find(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryAdjustmentService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new inventory adjustment", notes = "CpType: 1 = Products, 2 = Assets", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody InventoryAdjustmentRequest inventoryAdjustmentRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryAdjustmentService.insert(inventoryAdjustmentRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update inventory adjustment by id", notes = "Update role", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody InventoryAdjustmentUpdateRequest inventoryAdjustmentUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryAdjustmentService.update(inventoryAdjustmentUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete inventory adjustment by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryAdjustmentService.delete(id, httpServletRequest);
    }

    @PostMapping("/update-status")
    @ApiOperation(value = "Update status inventory adjustment by id", notes = "Status: 0 = Void, 1 = Issued, 2 = Fulfilled", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateStatus(@RequestBody BaseFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryAdjustmentService.updateStatus(filter, httpServletRequest);
    }

    @PostMapping("/find/qty-on-hand")
    @ApiOperation(value = "Find qty on hand by filter", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findQtyOnHand(@RequestBody QtyOnHandFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryAdjustmentService.findQtyOnHand(filter, httpServletRequest);
    }

}
