package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.RequestStockFilter;
import com.ut.nlSystemAPi.model.request.Login.ProductGroup.ProductGroupRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductGroup.ProductGroupUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.RequestStock.RequestStockApproveStatus;
import com.ut.nlSystemAPi.model.request.Login.RequestStock.RequestStockRequest;
import com.ut.nlSystemAPi.model.request.Login.RequestStock.RequestStockUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface RequestStockService {
  ResponseMessage<BaseResult> getList(RequestStockFilter requestStockFilter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(RequestStockRequest requestStockRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(RequestStockUpdateRequest requestStockUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> updateApproveStatus(RequestStockApproveStatus requestStockApproveStatus, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

}