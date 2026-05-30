package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.CalculateTotalProductFilter;
import com.ut.nlSystemAPi.model.filter.ProductGroupPriceFilter;
import com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice.ProductGroupPriceRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice.ProductGroupPriceUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface ProductGroupPriceService {

  ResponseMessage<BaseResult>  getList(ProductGroupPriceFilter productGroupPriceFilter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(ProductGroupPriceRequest productGroupPriceRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(ProductGroupPriceUpdateRequest productGroupPriceUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> calculateTotal(CalculateTotalProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> listTotal(CalculateTotalProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> listClone(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;
}