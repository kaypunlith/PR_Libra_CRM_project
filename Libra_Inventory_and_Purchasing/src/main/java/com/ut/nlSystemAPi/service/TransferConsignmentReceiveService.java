package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TransferConsignmentFilter;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentReceiveRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface TransferConsignmentReceiveService {

  ResponseMessage<BaseResult> getList(TransferConsignmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> receive(TransferConsignmentReceiveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

}