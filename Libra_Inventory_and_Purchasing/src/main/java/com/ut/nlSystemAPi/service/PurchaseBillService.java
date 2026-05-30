package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PurchaseBillFilter;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface PurchaseBillService {

  ResponseMessage<BaseResult> getList(PurchaseBillFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(PurchaseBillRequest purchaseBillRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(PurchaseBillUpdateRequest purchaseBillUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> approve(Long id, Long isApprove, HttpServletRequest httpServletRequest) throws UnknownHostException;

}
