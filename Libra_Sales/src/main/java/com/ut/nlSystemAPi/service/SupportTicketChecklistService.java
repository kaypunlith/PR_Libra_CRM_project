package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Checklist.ChecklistSaveRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface SupportTicketChecklistService {

    ResponseMessage<BaseResult> find(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> save(Long id, ChecklistSaveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;
}
