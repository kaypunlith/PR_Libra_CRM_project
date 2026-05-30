package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.GoodReceiptNoteFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.GoodReceiptNote.GoodReceiptNoteRequest;
import com.ut.nlSystemAPi.model.request.Login.GoodReceiptNote.GoodReceiptNoteUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface GoodReceiptNoteService {

 ResponseMessage<BaseResult> getList(GoodReceiptNoteFilter goodReceiptNoteFilter, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> getListVendor(GoodReceiptNoteFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> insert(GoodReceiptNoteRequest goodReceiptNoteRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> update(GoodReceiptNoteUpdateRequest goodReceiptNoteUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> updateApprove(StatusFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

}