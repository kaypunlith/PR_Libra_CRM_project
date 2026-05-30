package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.Currency;
import com.ut.nlSystemAPi.model.JournalEntry;
import com.ut.nlSystemAPi.model.JournalEntryDetail;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.JournalEntryFilter;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryFileRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryStatusRequest;
import com.ut.nlSystemAPi.model.response.Currency.CurrencyResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.ChartAccountDropdownResponse;
import com.ut.nlSystemAPi.model.response.JournalEntry.JournalEntryDetailResponse;
import com.ut.nlSystemAPi.model.response.JournalEntry.JournalEntryFileResponse;
import com.ut.nlSystemAPi.model.response.JournalEntry.JournalEntryResponse;
import com.ut.nlSystemAPi.model.response.JournalEntry.ReceivePaymentsResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntrySupervisorMapper {

  List<JournalEntryResponse> getList(@Param("filter") JournalEntryFilter filter);

  Long countList(@Param("filter") Filter filter);

  List<JournalEntryDetailResponse> getJournalEntryDetail(@Param("journalId") Long journalId);

  List<JournalEntryFileResponse> getJournalEntryFile(@Param("journalId") Long journalId);

  List<JournalEntryResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("journalEntry") JournalEntry journalEntry);

  Boolean insertGeneralLedgerDetail(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  Boolean insertReceivePayment(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  Boolean insertPayBill(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  Boolean insertJournalEntryFile(@Param("journalEntryFileRequest") JournalEntryFileRequest journalEntryFileRequest, @Param("journalId") Long journalId);

  Boolean update(@Param("journalEntry") JournalEntry journalEntry);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean deleteJournalEntryFile(@Param("glId") Long glId, @Param("userId") Long userId);

  Boolean deleteGeneralLedgerDetail(@Param("glId") Long glId);

  Boolean updateStatus(@Param("journalEntryStatusRequest") JournalEntryStatusRequest journalEntryStatusRequest, @Param("userId") Long userId);

  String getReLastReceivePaymentCode();

  String getReLastPayBillCode();

  List<ChartAccountDropdownResponse> getAccountChartNameById(@Param("id") Long id);

  List<ReceivePaymentsResponse> getReLastReceivePaymentByJournalId(@Param("id") Long id);

  List<ReceivePaymentsResponse> getReLastPayBillByJournalId(@Param("id") Long id);

  Long getReceivePaymentId(@Param("glId") Long glId);

  Long getPayBillId(@Param("glId") Long glId);

}