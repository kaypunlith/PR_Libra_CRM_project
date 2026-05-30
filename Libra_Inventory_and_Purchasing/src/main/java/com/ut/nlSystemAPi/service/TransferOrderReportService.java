package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TransferOrderReportByItemFilter;
import com.ut.nlSystemAPi.model.filter.TransferOrderReportFilter;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface TransferOrderReportService {

  ResponseMessage<BaseResult> getList(TransferOrderReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getListByItem(TransferOrderReportByItemFilter filterByItem, HttpServletRequest httpServletRequest) throws UnknownHostException;
}