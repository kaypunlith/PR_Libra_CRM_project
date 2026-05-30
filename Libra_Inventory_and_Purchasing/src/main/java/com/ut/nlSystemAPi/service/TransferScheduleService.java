package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TransferScheduleFilter;
import com.ut.nlSystemAPi.model.request.Login.Shipment.ShipmentRequest;
import com.ut.nlSystemAPi.model.request.Login.Shipment.ShipmentUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferSchedule.TransferScheduleRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferSchedule.TransferScheduleUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface TransferScheduleService {

  ResponseMessage<BaseResult> getList(TransferScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> insert(TransferScheduleRequest transferScheduleRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> update(TransferScheduleUpdateRequest transferScheduleUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

}