package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.filter.TermConditionApplyFilter;
import com.ut.nlSystemAPi.model.request.TermConditionApply.TermConditionApplyRequest;
import com.ut.nlSystemAPi.model.request.TermConditionApply.TermConditionApplyUpdateRequest;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;

import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface TermConditionApplyService {

  ResponseMessage<BaseResult> getList(TermConditionApplyFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(TermConditionApplyRequest termConditionApplyRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(TermConditionApplyUpdateRequest termConditionApplyUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

}