package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ARAPScheduleFilter;
import com.ut.nlSystemAPi.model.request.Login.ARAPSchedule.ARAPScheduleRequest;
import com.ut.nlSystemAPi.model.request.Login.ARAPSchedule.ARAPScheduleUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface APScheduleService {

  ResponseMessage<BaseResult> getListAPSchedule(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(ARAPScheduleRequest apScheduleRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(ARAPScheduleUpdateRequest apScheduleUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getVendorTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getVendorTopTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getVendorBookedTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getVendorBookedTopTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

}