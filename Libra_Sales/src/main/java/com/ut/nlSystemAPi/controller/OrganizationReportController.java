package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.Report.Organization.*;
import com.ut.nlSystemAPi.service.OrganizationReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/organization-report")
@Api(tags = "38. Organization Report", description = "Organization Report Resource")
public class OrganizationReportController {

    @Autowired
    private OrganizationReportService organizationReportService;

    @PostMapping("/account-receivable-aging")
    @ApiOperation(value = "List account receivable aging by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListAccountReceivableAging(@RequestBody AccountReceivableAgingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListAccountReceivableAging(filter, httpServletRequest);
    }

    @PostMapping("/customer-history")
    @ApiOperation(value = "List customer history by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListCustomerHistory(@RequestBody CustomerHistoryReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListCustomerHistory(filter, httpServletRequest);
    }

    @PostMapping("/customer-history-print/{id}")
    @ApiOperation(value = "Print customer history quotations", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> printCustomerHistoryQuotation(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.printCustomerHistoryQuotation(id, httpServletRequest);
    }

    @PostMapping("/customer-balance")
    @ApiOperation(value = "List customer balance by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListCustomerBalance(@RequestBody CustomerBalanceReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListCustomerBalance(filter, httpServletRequest);
    }

    @PostMapping("/customer-balance-by-invoice")
    @ApiOperation(value = "List customer balance by invoice with filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListCustomerBalanceByInvoice(@RequestBody CustomerBalanceByInvoiceReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListCustomerBalanceByInvoice(filter, httpServletRequest);
    }

    @PostMapping("/statement")
    @ApiOperation(value = "List statement with filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListStatement(@RequestBody StatementReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListStatement(filter, httpServletRequest);
    }

    @PostMapping("/customer-address")
    @ApiOperation(value = "List customer address with filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListCustomerAddress(@RequestBody CustomerAddressReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListCustomerAddress(filter, httpServletRequest);
    }

    @PostMapping("/customer-address-list")
    @ApiOperation(value = "List customer address list with filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListCustomerAddressList(@RequestBody CustomerAddressListReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListCustomerAddressList(filter, httpServletRequest);
    }

    @PostMapping("/customer-address-detail")
    @ApiOperation(value = "List customer address detail with filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListCustomerAddressDetail(@RequestBody CustomerAddressListReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListCustomerAddressDetail(filter, httpServletRequest);
    }

    @PostMapping("/so-balance")
    @ApiOperation(value = "List so balance with filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListSOBalance(@RequestBody SOBalanceReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListSOBalance(filter, httpServletRequest);
    }

    @PostMapping("/activity-card-tracking")
    @ApiOperation(value = "List activity card tracking with filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListActivityCardTracking(@RequestBody ActivityCardTrackingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.getListActivityCardTracking(filter, httpServletRequest);
    }

    @PostMapping("/update-memo-status")
    @ApiOperation(value = "Update memo print status", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateMemoStatus(@RequestBody com.ut.nlSystemAPi.model.request.Organization.CustomerHistoryPrintRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationReportService.updateMemoStatus(request, httpServletRequest);
    }
}
