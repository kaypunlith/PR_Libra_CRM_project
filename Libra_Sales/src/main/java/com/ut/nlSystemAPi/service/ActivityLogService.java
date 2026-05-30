package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ActivityFilter;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;

public interface ActivityLogService {

  ResponseMessage<BaseResult> getList(ActivityFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id,HttpServletRequest httpServletRequest) throws UnknownHostException;

  void insert(String endpoint, Long line, String bug, String moduleName, String moduleId, String act, int status, String description, LocalTime startDuration, LocalTime endDuration, HttpServletRequest httpServletRequest) throws UnknownHostException;

}