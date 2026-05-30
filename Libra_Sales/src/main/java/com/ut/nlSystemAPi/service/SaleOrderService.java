package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.StatusRequest;
import com.ut.nlSystemAPi.model.filter.QuotationFilter;
import com.ut.nlSystemAPi.model.filter.SaleOrderFilter;
import com.ut.nlSystemAPi.model.request.Quotation.*;
import com.ut.nlSystemAPi.model.request.SaleOrder.SaleOrderRequest;
import com.ut.nlSystemAPi.model.request.SaleOrder.SaleOrderUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface SaleOrderService {

  ResponseMessage<BaseResult> getList(SaleOrderFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, Long warehouseId, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(SaleOrderRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(SaleOrderUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> approve(StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> close(StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

}