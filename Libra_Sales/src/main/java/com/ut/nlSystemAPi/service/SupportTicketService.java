package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.SupportTicketFilter;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTicketAddMoreRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTicketRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTicketUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface SupportTicketService {

    ResponseMessage<BaseResult> getList(SupportTicketFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(SupportTicketRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(SupportTicketUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getAddMore(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> addMore(Long id, SupportTicketAddMoreRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;
}
