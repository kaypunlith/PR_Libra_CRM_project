package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.Report.Sales.*;
import com.ut.nlSystemAPi.service.SalesReportService;
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
@RequestMapping("/sales-report")
@Api(tags = "37. Sales Report", description = "Sales Report Resource")
public class SalesReportController {

    @Autowired
    private SalesReportService salesReportService;

    @PostMapping("/customer-summary")
    @ApiOperation(value = "List customer summary by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListCustomerSummary(@RequestBody CustomerSummaryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListCustomerSummary(filter, httpServletRequest);
    }

    @PostMapping("/quotation")
    @ApiOperation(value = "List quotation by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListQuotation(@RequestBody QuotationReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListQuotation(filter, httpServletRequest);
    }

    @PostMapping("/sales-order")
    @ApiOperation(value = "List sales order by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListSalesOrder(@RequestBody SalesOrderReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListSalesOrder(filter, httpServletRequest);
    }

    @PostMapping("/sales-top-button-item")
    @ApiOperation(value = "List sales top button item by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListSalesTopButtonItem(@RequestBody SalesTopButtonReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListSalesTopButtonItem(filter, httpServletRequest);
    }

    @PostMapping("/sales-by-item")
    @ApiOperation(value = "List sales by item by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListSalesByItem(@RequestBody SalesByItemReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListSalesByItem(filter, httpServletRequest);
    }

    @PostMapping("/sales-by-item-type")
    @ApiOperation(value = "List sales by item type filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListSalesByItemType(@RequestBody SalesByItemTypeReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListSalesByItemType(filter, httpServletRequest);
    }

    @PostMapping("/sales-by-customer")
    @ApiOperation(value = "List sales by customer by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListSalesByCustomer(@RequestBody SalesByCustomerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListSalesByCustomer(filter, httpServletRequest);
    }

    @PostMapping("/sales-by-rep")
    @ApiOperation(value = "List sales by rep by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListSalesByRep(@RequestBody SalesByCustomerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListSalesByRep(filter, httpServletRequest);
    }

    @PostMapping("/sales-top-button-customer")
    @ApiOperation(value = "List sales top button customer by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListSalesTopButtonCustomer(@RequestBody SalesTopButtonReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListSalesTopButtonCustomer(filter, httpServletRequest);
    }

    @PostMapping("/total-sales")
    @ApiOperation(value = "List total sales by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListTotalSales(@RequestBody TotalSalesReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListTotalSales(filter, httpServletRequest);
    }

    @PostMapping("/sales-invoice")
    @ApiOperation(value = "List sales invoice by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListSalesInvoice(@RequestBody SalesInvoiceReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListSalesInvoice(filter, httpServletRequest);
    }

    @PostMapping("/invoice-by-rep")
    @ApiOperation(value = "List invoice by rep by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListInvoiceByRep(@RequestBody InvoiceByRepReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListInvoiceByRep(filter, httpServletRequest);
    }

    @PostMapping("/invoice-credit-memo")
    @ApiOperation(value = "List invoice credit memo by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListInvoiceCreditMemo(@RequestBody InvoiceCreditMemoReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListInvoiceCreditMemo(filter, httpServletRequest);
    }

    @PostMapping("/open-invoice-by-rep")
    @ApiOperation(value = "List open invoice by rep by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListOpenInvoiceByRep(@RequestBody OpenInvoiceByRepReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListOpenInvoiceByRep(filter, httpServletRequest);
    }

    @PostMapping("/discount-summary")
    @ApiOperation(value = "List discount summary by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListDiscountSummary(@RequestBody DiscountSummaryReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListDiscountSummary(filter, httpServletRequest);
    }

    @PostMapping("/delivery-note")
    @ApiOperation(value = "List delivery note by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListDeliveryNote(@RequestBody DeliveryNoteReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListDeliveryNote(filter, httpServletRequest);
    }

    @PostMapping("/ecommerce-user")
    @ApiOperation(value = "List e-commerce user by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListECommerceUser(@RequestBody ECommerceUserReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListECommerceUser(filter, httpServletRequest);
    }

    @PostMapping("/receive-payment")
    @ApiOperation(value = "List receive payment by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListReceivePayment(@RequestBody ReceivePaymentReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListReceivePayment(filter, httpServletRequest);
    }

    @PostMapping("/receive-payment-by-rep")
    @ApiOperation(value = "List receive payment by rep by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListReceivePaymentByRep(@RequestBody ReceivePaymentByRepReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return salesReportService.getListReceivePaymentByRep(filter, httpServletRequest);
    }
}
