package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PurchaseReceiveFilter;
import com.ut.nlSystemAPi.model.request.Login.PurchaseReceive.PurchaseReceiveSaveRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface PurchaseReceiveService {

  ResponseMessage<BaseResult> getList(PurchaseReceiveFilter purchaseReceiveFilter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOneReceive(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> save(PurchaseReceiveSaveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

}