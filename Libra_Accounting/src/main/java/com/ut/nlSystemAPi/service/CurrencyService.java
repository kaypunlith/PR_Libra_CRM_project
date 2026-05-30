package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.Currency.CurrencyRequest;
import com.ut.nlSystemAPi.model.request.Login.Currency.CurrencyUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface CurrencyService {

  ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(CurrencyRequest currencyRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(CurrencyUpdateRequest currencyUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

}