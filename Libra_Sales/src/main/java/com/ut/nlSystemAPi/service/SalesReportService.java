package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.Report.Sales.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface SalesReportService {

    ResponseMessage<BaseResult> getListCustomerSummary(CustomerSummaryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListQuotation(QuotationReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListSalesOrder(SalesOrderReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListSalesTopButtonItem(SalesTopButtonReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListSalesTopButtonCustomer(SalesTopButtonReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListSalesByItem(SalesByItemReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListSalesByItemType(SalesByItemTypeReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListSalesByCustomer(SalesByCustomerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListSalesByRep(SalesByCustomerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListTotalSales(TotalSalesReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListSalesInvoice(SalesInvoiceReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListInvoiceByRep(InvoiceByRepReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListInvoiceCreditMemo(InvoiceCreditMemoReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListOpenInvoiceByRep(OpenInvoiceByRepReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListDiscountSummary(DiscountSummaryReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListDeliveryNote(DeliveryNoteReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListECommerceUser(ECommerceUserReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListReceivePayment(ReceivePaymentReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListReceivePaymentByRep(ReceivePaymentByRepReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

}
