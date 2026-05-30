package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.CompanyCurrencyFilter;
import com.ut.nlSystemAPi.model.request.Login.CompnayCurrency.CompanyCurrencyRequest;
import com.ut.nlSystemAPi.model.request.Login.CompnayCurrency.CompanyCurrencyUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface CompanyCurrencyService {

  ResponseMessage<BaseResult> getList(CompanyCurrencyFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(CompanyCurrencyRequest companyCurrencyRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(CompanyCurrencyUpdateRequest companyCurrencyUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> applyPos(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

}