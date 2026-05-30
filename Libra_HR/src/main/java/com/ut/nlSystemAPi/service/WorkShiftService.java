package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.WorkShiftRequest;
import com.ut.nlSystemAPi.model.request.WorkShiftUpdateRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface WorkShiftService {

    ResponseMessage<BaseResult> insert(WorkShiftRequest workShiftRequest, Long creator, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id);

    ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(WorkShiftUpdateRequest workShiftUpdateRequest, Long modifierId);

    ResponseMessage<BaseResult> delete(Long id, Long userId);

    ResponseMessage<BaseResult> getListDropDown(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListWorkShiftType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

}