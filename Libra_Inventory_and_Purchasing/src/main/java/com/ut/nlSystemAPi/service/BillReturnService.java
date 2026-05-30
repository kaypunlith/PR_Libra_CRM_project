package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.BillReturnFilter;
import com.ut.nlSystemAPi.model.filter.BillReturnReceiptFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.BillReturn.BillReturnRequest;
import com.ut.nlSystemAPi.model.request.Login.BillReturn.BillReturnUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.BillReturn.PayBillReturnRequest;
import com.ut.nlSystemAPi.model.request.Login.BillReturn.PayBillReturnWithPbsRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.List;

public interface BillReturnService {

  ResponseMessage<BaseResult> getList(BillReturnFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(BillReturnRequest billReturnRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(BillReturnUpdateRequest billReturnUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> pick(StatusFilter statusFilter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> pay(PayBillReturnRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> applyPb(PayBillReturnWithPbsRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> voidReceipt(BillReturnReceiptFilter request, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> listReceipt(BillReturnReceiptFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

}