package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;

import com.ut.nlSystemAPi.model.filter.ChartAccountGroupFilter;

import com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup.ChartAccountGroupRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup.ChartAccountGroupUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup.ChartAccountGroupUpdateStatusRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeUpdateStatusRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface ChartAccountGroupService {


    ResponseMessage<BaseResult> getList(ChartAccountGroupFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;


    ResponseMessage<BaseResult> updateStatus(ChartAccountGroupUpdateStatusRequest updateStatusRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(ChartAccountGroupRequest addRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(ChartAccountGroupUpdateRequest updateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;
}
