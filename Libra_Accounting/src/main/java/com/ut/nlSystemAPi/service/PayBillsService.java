package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PayBillPrintFilter;
import com.ut.nlSystemAPi.model.filter.PayBillsFilter;
import com.ut.nlSystemAPi.model.request.Login.PayBills.PayBillRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface PayBillsService {

  ResponseMessage<BaseResult> getList(PayBillsFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> save(PayBillRequest updateRequest, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getListPrint(PayBillPrintFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

}