package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.service.PurchasingReportService;
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
@RequestMapping("/purchasing-report")
@Api(tags = "41. Purchasing  Report", description = "Purchasing  Report Resource")
@Timed
public class PurchasingReportController {
    @Autowired
    private PurchasingReportService purchasingReportService;

    @PostMapping("/purchase-bill-barcode")
    @ApiOperation(value = " purchase-bill-barcode by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPurchaseBillBarcode(@RequestBody PurchasingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchasingReportService.getList(filter, httpServletRequest);
    }

    @PostMapping("/invoice-purchase-bill")
    @ApiOperation(value = "invoice purchase bill item by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listInvoicePurchaseBill(@RequestBody InvoicePurchaseBillReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchasingReportService.getInvoicePurchaseBill(filter, httpServletRequest);
    }

    @PostMapping("/invoice-bill-return")
    @ApiOperation(value = "invoice bill return item by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listInvoicePurchaseBillReturn(@RequestBody InvoicePurchaseBillReturnReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchasingReportService.getInvoicePurchaseBillReturn(filter, httpServletRequest);
    }

    @PostMapping("/purchase-by-item")
    @ApiOperation(value = "List purchase by item by filter", notes = "View: 1 = Item Summary, 2 = Item Detail, 3 = Parent Summary;  Type: 1 = Product, 2 = Service, 3 = Miscellaneous;",authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPurchaseByItem(@RequestBody PurchaseByItemFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchasingReportService.getListPurchaseByItem(filter, httpServletRequest);
    }

    @PostMapping("/pay-bill-report")
    @ApiOperation(value = "List pay bill report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPayBill(@RequestBody PayBillReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchasingReportService.getListPayBill(filter, httpServletRequest);
    }
}