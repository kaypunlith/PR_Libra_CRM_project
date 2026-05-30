package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.DashboardFilter;
import com.ut.nlSystemAPi.service.DashboardAccountingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/dashboard-accounting")
@Api(tags = "39. Dashboard", description = "Dashboard Resource")
public class DashboardAccountingController {

    @Autowired
    private DashboardAccountingService dashboardAccountingService;

    @PostMapping("/list-growthRate")
    @ApiOperation(value = "List saleTargetVsActual filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listGrowthRate(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListGrowthRate(filter, httpServletRequest);
    }

    @PostMapping("/list-profit-loss")
    @ApiOperation(value = "List branch by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listProfitLoss(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getList(filter, httpServletRequest);
    }

    @PostMapping("/list-revenue")
    @ApiOperation(value = "List revenue by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listRevenue(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListRevenue(filter, httpServletRequest);
    }

    @PostMapping("/list-expenditure")
    @ApiOperation(value = "List expenditure by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listExpenditure(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListExpenditure(filter, httpServletRequest);
    }

    @PostMapping("/list-total-saleByQuarter")
    @ApiOperation(value = "List saleTargetVsActual filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listTotalByQuarter(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListTotalByQuarter(filter, httpServletRequest);
    }

    @PostMapping("/list-sale-targetVsActual")
    @ApiOperation(value = "List saleTargetVsActual filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listSaleTargetVsActual(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListSaleTargetVsActual(filter, httpServletRequest);
    }

    @PostMapping("/list-customer-segmentation")
    @ApiOperation(value = "List customer segmentation by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listCustomerSegmentation(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListCustomerSegmentation(filter, httpServletRequest);
    }

    @PostMapping("/list-sale-top-customer")
    @ApiOperation(value = "List sale to customer by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listSaleTopCustomer(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListSaleTopCustomer(filter, httpServletRequest);
    }

    @PostMapping("/list-invoice-status")
    @ApiOperation(value = "List sale to status by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listSaleStatus(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.listSaleStatus(filter, httpServletRequest);
    }

    @PostMapping("/list-sale-top-item")
    @ApiOperation(value = "List sale to customer by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listSaleTopItem(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.listSaleTopItem(filter, httpServletRequest);
    }

    @PostMapping("/list-invoice-currencies")
    @ApiOperation(value = "List invoice currencies by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listInvoiceCurrencies(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListInvoiceCurrencies(filter, httpServletRequest);
    }

    @PostMapping("/list-sales-by-productGroup")
    @ApiOperation(value = "List sale  by productGroup filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listSalesByProductGroup(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListSaleByProductGroup(filter, httpServletRequest);
    }

    @PostMapping("/list-total-sales-yearToDate")
    @ApiOperation(value = "List sale Year To Date filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listSalesYearToDate(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListSaleYearToDate(filter, httpServletRequest);
    }

    @PostMapping("/list-purchase-to-vendor")
    @ApiOperation(value = "List purchase to top vendor filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listPurchaseToVendor(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListPurchaseToVendor(filter, httpServletRequest);
    }

    @PostMapping("/list-purchase-currencies")
    @ApiOperation(value = "List purchase to top vendor filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listPurchaseCurrencies(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListPurchaseCurrencies(filter, httpServletRequest);
    }

    @PostMapping("/list-total-purchase-yearToDate")
    @ApiOperation(value = "List purchase to top vendor filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listTotalPurchaseYearToDate(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListTotalPurchaseYearToDate(filter, httpServletRequest);
    }

    @PostMapping("/list-graph")
    @ApiOperation(value = "List saleTargetVsActual filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listGrab(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getListGraph(filter, httpServletRequest);
    }





    @PostMapping("/list-account-payable-aging")
    @ApiOperation(value = "Get aging report", notes = "Get aging report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getAccountPayable(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getAccountPayableAging(filter, httpServletRequest);
    }

    // @PostMapping("/list-graphic-account-payable-aging")
    // @ApiOperation(value = "Get aging report", notes = "Get aging report by filter", authorizations = {@Authorization(value = "Bearer")})
    // public ResponseMessage<BaseResult> GraphicAccountPayable(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    //     // Check Header Token
    //     if (UserAuthSession.getUserAuth() == null) {
    //         return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    //     }
    //     return dashboardAccountingService.getAccountGraphicPayable(filter, httpServletRequest);
    // }





    @PostMapping("/list-account-receivable-aging")
    @ApiOperation(value = "Get aging report", notes = "Get aging report by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getAccountReceivable(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dashboardAccountingService.getAccountReceivable(filter, httpServletRequest);
    }

    // @PostMapping("/list-graphic-account-receivable-aging")
    // @ApiOperation(value = "Get aging report", notes = "Get aging report by filter", authorizations = {@Authorization(value = "Bearer")})
    // public ResponseMessage<BaseResult> GraphicAccountReconcile(@RequestBody DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    //     // Check Header Token
    //     if (UserAuthSession.getUserAuth() == null) {
    //         return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
    //     }
    //     return dashboardAccountingService.getAccountGraphicReconcile(filter, httpServletRequest);
    // }




}
