package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.AccountClosingDateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface AccountClosingDateService {

  ResponseMessage<BaseResult> getList(HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> add(AccountClosingDateRequest accountClosingDateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

}