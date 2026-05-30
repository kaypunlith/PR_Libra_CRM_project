package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.Report.Organization.*;
import com.ut.nlSystemAPi.model.filter.Report.Sales.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface OrganizationReportService {

    ResponseMessage<BaseResult> getListAccountReceivableAging(AccountReceivableAgingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListCustomerBalance(CustomerBalanceReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListCustomerBalanceByInvoice(CustomerBalanceByInvoiceReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListStatement(StatementReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListCustomerAddress(CustomerAddressReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListCustomerAddressList(CustomerAddressListReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListCustomerAddressDetail(CustomerAddressListReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListSOBalance(SOBalanceReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListActivityCardTracking(ActivityCardTrackingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

}