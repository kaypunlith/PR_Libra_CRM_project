package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.JournalEntryFilter;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryNoteRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryStatusRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface JournalEntrySupervisorService {

  ResponseMessage<BaseResult> getList(JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(JournalEntryRequest journalEntryRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(JournalEntryUpdateRequest journalEntryUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> updateStatus(JournalEntryStatusRequest journalEntryStatusRequest, HttpServletRequest httpServletRequest) throws UnknownHostException;

}