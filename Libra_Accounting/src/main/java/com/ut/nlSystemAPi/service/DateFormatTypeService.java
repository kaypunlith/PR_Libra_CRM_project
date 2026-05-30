package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.BranchType.BranchTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.BranchType.BranchTypeUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.DateFormatType.DateFormatTypeRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface DateFormatTypeService {

    ResponseMessage<BaseResult> insert(DateFormatTypeRequest dateFormatTypeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;
}
