package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.PriceRequestFilter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.PriceRequest.*;
import com.ut.nlSystemAPi.model.request.Login.Product.ProductRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface PriceRequestService {

    ResponseMessage<BaseResult> getList(PriceRequestFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> print(Long customerId,HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(PriceRequest_Request priceRequest_request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(PriceRequest_RequestUpdate priceRequest_requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> updateStatus(PriceRequestStatusUpdate statusUpdate, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> sendTelegram(SendToTelegram sendToTelegram, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> convert(ProductRequest productRequest, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> sumListApprove(HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> listApprove(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> approve(PriceRequestApproveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

}
