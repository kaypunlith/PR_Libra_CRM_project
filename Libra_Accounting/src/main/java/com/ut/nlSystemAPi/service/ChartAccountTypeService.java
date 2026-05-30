package com.ut.nlSystemAPi.service;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeUpdateStatusRequest;
import org.springframework.validation.BindingResult;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeUpdateRequest;

public interface ChartAccountTypeService {

    ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> updateStatus(ChartAccountTypeUpdateStatusRequest chartAccountTypeUpdateStatusRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(ChartAccountTypeRequest accountTypeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(ChartAccountTypeUpdateRequest accountTypeUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;
}
