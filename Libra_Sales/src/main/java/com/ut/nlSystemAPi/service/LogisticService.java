package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LogisticFilter;
import com.ut.nlSystemAPi.model.request.Logistic.LogisticDeliveryRequest;
import com.ut.nlSystemAPi.model.request.Logistic.LogisticMergeRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface LogisticService  {

  ResponseMessage<BaseResult> getList(LogisticFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> merge(LogisticMergeRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOneDelivery(Long id, Long type, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delivery(LogisticDeliveryRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

}