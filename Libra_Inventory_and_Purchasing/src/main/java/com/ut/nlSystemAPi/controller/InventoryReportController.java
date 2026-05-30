package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.service.InventoryReportService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/inventory-report")
@Api(tags = "39. Inventory Report", description = "Inventory Report Resource")
@Timed
public class InventoryReportController {

    @Autowired
    private InventoryReportService inventoryReportService;

    @PostMapping("/adjustment/list")
    @ApiOperation(value = "List inventory adjustment report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listInventoryAdjustment(@RequestBody InventoryAdjustmentReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.getListInventoryAdjustment(filter, httpServletRequest);
    }

    @PostMapping("/adjustment/by-item-list")
    @ApiOperation(value = "List inventory adjustment report by item", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listInventoryAdjustmentByItem(@RequestBody InventoryAdjustmentReportByItemFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.getListInventoryAdjustmentByItem(filter, httpServletRequest);
    }

    @PostMapping("/stock-available-for-sale/list")
    @ApiOperation(value = "List inventory adjustment report by item", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listStockAvailableForSale(@RequestBody StockAvailableForSaleReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.listStockAvailableForSale(filter, httpServletRequest);
    }

    @PostMapping("/global-inventory/list")
    @ApiOperation(value = "List global inventory report by item", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listGlobalInventory(@RequestBody GlobalInventoryReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
//        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.listGlobalInventory(filter, httpServletRequest);
    }

    @PostMapping("/inventory-activity/list")
    @ApiOperation(value = "List inventory activity report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listInventoryActivity(@RequestBody InventoryActivityReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.listInventoryActivity(filter, httpServletRequest);
    }

    @PostMapping("/product-average-cost/list")
    @ApiOperation(value = "List product average cost report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProductAverageCost(@RequestBody ProductAverageCostReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.listProductAverageCost(filter, httpServletRequest);
    }

    @PostMapping("/product-price-list/list")
    @ApiOperation(value = "List product price list report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProductPriceList(@RequestBody ProductPriceListReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.listProductPriceList(filter, httpServletRequest);
    }

    @PostMapping("/valuation/list")
    @ApiOperation(value = "List valuation report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listValuation(@RequestBody ValuationReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.listValuation(filter, httpServletRequest);
    }

    @PostMapping("/price-request-tracking/list")
    @ApiOperation(value = "List price request tracking report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPriceRequestTracking(@RequestBody PriceRequestTrackingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.listPriceRequestTracking(filter, httpServletRequest);
    }

    @PostMapping("/product-expiry-date/list")
    @ApiOperation(value = "List product expiry date report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> productExpiryDate(@RequestBody ProductExpiryDate filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventoryReportService.productExpiryDate(filter, httpServletRequest);
    }

}