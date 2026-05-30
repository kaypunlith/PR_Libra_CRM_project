package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ICS.ICSRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.List;

public interface ICSService {

  ResponseMessage<BaseResult> getList(HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(ICSRequest icsRequests, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

}