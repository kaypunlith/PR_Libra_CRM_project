package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.JournalEntry;
import com.ut.nlSystemAPi.model.JournalEntryDetail;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.JournalEntryFilter;
import com.ut.nlSystemAPi.model.filter.MakeDepositFilter;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryFileRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryNoteRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryStatusRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.ChartAccountDropdownResponse;
import com.ut.nlSystemAPi.model.response.JournalEntry.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntryMapper {

  List<JournalEntryResponse> getList(@Param("filter") JournalEntryFilter filter);

  Long countList(@Param("filter") Filter filter);
  List<JournalEntryDetailResponse> getListReportByGroup(@Param("filter") JournalEntryFilter filter);

  Long countListReportByGroup(@Param("filter") JournalEntryFilter filter);

  List<JournalEntryDetailResponse> getJournalEntryDetail(@Param("journalId") Long journalId);

  List<JournalEntryDetailResponse> getJournalEntryDetailByJournalIds(@Param("journalIds") List<Long> journalIds, @Param("chartAccountGroupId") Long chartAccountGroupId, @Param("chartAccountId") Long chartAccountId);

  List<JournalEntryFileResponse> getJournalEntryFile(@Param("journalId") Long journalId);

  List<JournalEntryFileResponse> getJournalEntryFilesByJournalIds(@Param("journalIds") List<Long> journalIds);

  Long getChartAccountGroupType(@Param("chartAccountGroupId") Long chartAccountGroupId);

  List<JournalEntryResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("journalEntry") JournalEntry journalEntry);

  Boolean insertGeneralLedgerDetail(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  Boolean insertReceivePayment(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  Boolean deleteReceivePaymentByGLId(@Param("generalLedgerId") Long generalLedgerId, @Param("userId") Long userId);

  Boolean insertPayBill(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  Boolean deletePayBillByGLId(@Param("generalLedgerId") Long generalLedgerId, @Param("userId") Long userId);

  Boolean insertJournalEntryFile(@Param("journalEntryFileRequest") JournalEntryFileRequest journalEntryFileRequest, @Param("journalId") Long journalId);

  Boolean update(@Param("journalEntry") JournalEntry journalEntry);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean deleteJournalEntryFile(@Param("glId") Long glId, @Param("userId") Long userId);

  Boolean deleteGeneralLedgerDetail(@Param("glId") Long glId);

  Boolean closeRecurrence(@Param("glId") Long glId, @Param("userId") Long userId);

  Boolean addRecurrence(@Param("glId") Long glId, @Param("userId") Long userId);

  Boolean updateStatus(@Param("request") JournalEntryStatusRequest request, @Param("userId") Long userId);

  String getReLastReceivePaymentCode();

  String getReLastPayBillCode();

  List<ChartAccountDropdownResponse> getAccountChartNameById(@Param("id") Long id);

  List<ReceivePaymentsResponse> getReLastReceivePaymentByJournalId(@Param("id") Long id);

  List<ReceivePaymentsResponse> getReLastPayBillByJournalId(@Param("id") Long id);

  Long getReceivePaymentId(@Param("glId") Long glId);

  String getPayBillId(@Param("glId") Long glId);

  Long checkReference(@Param("reference") String reference);

  List<MakeDepositApplyToResponse> getDepositFromByGlId(@Param("id") Long id);

  //! Purchase Request
  List<MakeDepositApplyToResponse> getPurchaseRequestList(@Param("filter") MakeDepositFilter filter);

  List<MakeDepositApplyToResponse> getOnePrDepositById(@Param("id") Long id);

  Long countPurchaseRequestList(@Param("filter") MakeDepositFilter filter);

  Double getPrDepositAmountById(@Param("id") Long id);

  Boolean updatePrDepositAmount(@Param("totalDeposit") Double totalDeposit, @Param("id") Long id);

  //! Purchase Order
  List<MakeDepositApplyToResponse> getPurchaseOrderList(@Param("filter") MakeDepositFilter filter);

  List<MakeDepositApplyToResponse> getOnePoDepositById(@Param("id") Long id);

  Long countPurchaseOrderList(@Param("filter") MakeDepositFilter filter);

  Double getPoDepositAmountById(@Param("id") Long id);

  Boolean updatePoDepositAmount(@Param("totalDeposit") Double totalDeposit, @Param("id") Long id);

  //! Quotations
  List<MakeDepositApplyToResponse> getQuotationsList(@Param("filter") MakeDepositFilter filter);

  List<MakeDepositApplyToResponse> getOneQTDepositById(@Param("id") Long id);

  Long countQuotationsList(@Param("filter") MakeDepositFilter filter);

  Double getQTDepositAmountById(@Param("id") Long id);

  Boolean updateQTDepositAmount(@Param("totalDeposit") Double totalDeposit, @Param("id") Long id);

  //! Sale order
  List<MakeDepositApplyToResponse> getSaleOrderList(@Param("filter") MakeDepositFilter filter);

  List<MakeDepositApplyToResponse> getOneSODepositById(@Param("id") Long id);

  Long countSaleOrderList(@Param("filter") MakeDepositFilter filter);

  Double getSODepositAmountById(@Param("id") Long id);

  String getCOA(@Param("glId") Long glId);

  String getPaidTo(@Param("glId") Long glId);

  Boolean updateSODepositAmount(@Param("totalDeposit") Double totalDeposit, @Param("id") Long id);

  Boolean note(@Param("request") JournalEntryNoteRequest request);

  String getJournalEntryType(@Param("glId") Long glId);
}
