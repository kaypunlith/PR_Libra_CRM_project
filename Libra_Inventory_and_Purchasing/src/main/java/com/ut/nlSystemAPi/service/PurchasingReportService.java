package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface PurchasingReportService {

  ResponseMessage<BaseResult> getList(PurchasingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getInvoicePurchaseBill(InvoicePurchaseBillReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getInvoicePurchaseBillReturn(InvoicePurchaseBillReturnReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getListPurchaseByItem(PurchaseByItemFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getListPayBill(PayBillReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

}
