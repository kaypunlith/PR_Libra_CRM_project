package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ExpenseRequestFilter;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.ApproveRequest;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.ExpenseRequestRequest;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.ExpenseRequestUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.CloseRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface ExpenseRequestService {

  ResponseMessage<BaseResult> getList(ExpenseRequestFilter expenseRequestFilter, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> getOne(Long id, ExpenseRequestFilter expenseRequestFilter, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> insert(ExpenseRequestRequest expenseRequestRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> update(ExpenseRequestUpdateRequest expenseRequestUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> updateClose(CloseRequest closeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> updateApprove(ApproveRequest approveRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> getListApprove(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

}