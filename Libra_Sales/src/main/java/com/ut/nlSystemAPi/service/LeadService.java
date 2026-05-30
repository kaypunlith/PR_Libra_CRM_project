package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LeadActivityCardFilter;
import com.ut.nlSystemAPi.model.filter.LeadFilter;
import com.ut.nlSystemAPi.model.request.Lead.LeadActivityCardRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadConvertRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface LeadService {

    ResponseMessage<BaseResult> getList(LeadFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(LeadRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(LeadUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> addActivityCard(LeadActivityCardRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListActivityCard(LeadActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOneActivityCard(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getConvert(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> convert(LeadConvertRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;
}
