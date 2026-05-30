package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.StatusRequest;
import com.ut.nlSystemAPi.model.filter.CreditMemoReceiptFilter;
import com.ut.nlSystemAPi.model.filter.SaleInvoiceFilter;
import com.ut.nlSystemAPi.model.filter.SalesInvoiceReceiptFilter;
import com.ut.nlSystemAPi.model.request.CreditMemo.ApplyWithInvoiceRequest;
import com.ut.nlSystemAPi.model.request.CreditMemo.CreditMemoPayRequest;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SaleInvoiceRequest;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SaleInvoiceUpdateRequest;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SalesInvoicePayRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface SaleInvoiceService {

  ResponseMessage<BaseResult> getList(SaleInvoiceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOneByCode(String code, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(SaleInvoiceRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(SaleInvoiceUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> close(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> closeRecurrence(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> approve(StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> pay(SalesInvoicePayRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> listReceipt(SalesInvoiceReceiptFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> voidReceipt(SalesInvoiceReceiptFilter request, HttpServletRequest httpServletRequest) throws UnknownHostException;

}
