package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportPipelineSettingRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportPipelineSettingUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface SupportPipelineSettingService {

    ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(SupportPipelineSettingRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(SupportPipelineSettingUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;
}
