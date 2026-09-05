package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ServiceFilter;
import com.ut.nlSystemAPi.model.filter.ServiceTerminateFilter;
import com.ut.nlSystemAPi.model.request.Service.ServiceRequest;
import com.ut.nlSystemAPi.model.request.Service.ServiceUpdateRequest;
import com.ut.nlSystemAPi.model.request.serviceTerminate.ServiceTerminateRequest;
import com.ut.nlSystemAPi.model.request.serviceTerminate.ServiceUpdateTerminateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface ServiceTerminateService {

    ResponseMessage<BaseResult> getList(ServiceTerminateFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListCustomerQuotation(Long customerId, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(ServiceTerminateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(ServiceUpdateTerminateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;
}