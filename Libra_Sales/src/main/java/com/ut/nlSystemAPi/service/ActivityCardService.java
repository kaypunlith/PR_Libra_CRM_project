package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ActivityCardFilter;
import com.ut.nlSystemAPi.model.request.ActivityCard.ActivityCardRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface ActivityCardService {

    ResponseMessage<BaseResult> getList(ActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListReport(ActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(ActivityCardRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;
}
