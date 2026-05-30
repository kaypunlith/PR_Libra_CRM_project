package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.UpdateStatusRequest;
import com.ut.nlSystemAPi.model.request.SalesTarget.SalesTargetRequest;
import com.ut.nlSystemAPi.model.request.SalesTarget.SalesTargetUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface SalesTargetService {

    ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(SalesTargetRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(SalesTargetUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> approve(UpdateStatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> close(UpdateStatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getEmployeeTarget(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;
}