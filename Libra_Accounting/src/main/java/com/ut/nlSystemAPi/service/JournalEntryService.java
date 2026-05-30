package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.JournalEntryFilter;
import com.ut.nlSystemAPi.model.filter.MakeDepositFilter;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.*;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface JournalEntryService {

  ResponseMessage<BaseResult> getList(JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getListReport(JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getListReportByGroup(JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(JournalEntryRequest journalEntryRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> writeChecks(WriteChecksRequest writeChecksRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> updateWriteChecks(WriteChecksRequestUpdate requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> findCheckById(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> makeDeposits(MakeDepositsRequest makeDepositsRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> updateMakeDeposits(MakeDepositsRequestUpdate requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> findDepositsById(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(JournalEntryUpdateRequest journalEntryUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> closeRecurrence(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> addRecurrence(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> updateStatus(JournalEntryStatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> checkReference(String reference, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getMakeDepositApplyToList(MakeDepositFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> note(JournalEntryNoteRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

}