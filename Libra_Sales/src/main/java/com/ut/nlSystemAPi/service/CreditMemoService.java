package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.CreditMemoReceiptFilter;
import com.ut.nlSystemAPi.model.filter.CreditMemoFilter;
import com.ut.nlSystemAPi.model.request.CreditMemo.ApplyWithInvoiceRequest;
import com.ut.nlSystemAPi.model.request.CreditMemo.CreditMemoRequest;
import com.ut.nlSystemAPi.model.request.CreditMemo.CreditMemoUpdateRequest;
import com.ut.nlSystemAPi.model.request.CreditMemo.CreditMemoPayRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface CreditMemoService {

    ResponseMessage<BaseResult> getList(CreditMemoFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(CreditMemoRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(CreditMemoUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> receive(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> pay(CreditMemoPayRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> applyWithInvoice(ApplyWithInvoiceRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> listReceipt(CreditMemoReceiptFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> voidReceipt(CreditMemoReceiptFilter request, HttpServletRequest httpServletRequest) throws UnknownHostException;
}