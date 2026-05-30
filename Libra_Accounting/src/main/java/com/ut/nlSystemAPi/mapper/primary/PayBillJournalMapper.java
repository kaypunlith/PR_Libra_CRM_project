package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.JournalEntry;
import com.ut.nlSystemAPi.model.JournalEntryDetail;
import com.ut.nlSystemAPi.model.PayBills;
import com.ut.nlSystemAPi.model.PayBillsDetail;
import com.ut.nlSystemAPi.model.filter.PayBillPrintFilter;
import com.ut.nlSystemAPi.model.filter.PayBillsFilter;
import com.ut.nlSystemAPi.model.filter.PayBillsJournalFilter;
import com.ut.nlSystemAPi.model.response.PayBillJournal.PayBillsJournalResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsDebitDataResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsPrintDetailResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsPrintResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentFileResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayBillJournalMapper {

  List<PayBillsJournalResponse> getList(@Param("filter") PayBillsJournalFilter filter, @Param("tableName") String tableName);

  Long countList(@Param("filter") PayBillsJournalFilter filter, @Param("tableName") String tableName);

  String getReLastPayBillCode();

  Boolean insertApAgings(@Param("payBills") PayBills payBills);

  Boolean insertApAgingDetail(@Param("payBillsDetail") PayBillsDetail payBillsDetail);

  Boolean insertGeneralLedger(@Param("journalEntry") JournalEntry journalEntry);

  Boolean insertGeneralLedgerDetail(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  List<PayBillsPrintResponse> getListPrint(@Param("filter") PayBillPrintFilter filter);

  Long countListPrint(@Param("filter") PayBillPrintFilter filter);

  List<ReceivePaymentFileResponse> getGlFile(@Param("glId") Long glId);

  List<PayBillsPrintResponse> find(@Param("id") Long id);

  List<PayBillsPrintDetailResponse> gePayBillNormalDetail(@Param("apAgingId") Long apAgingId);

  Boolean createTable(@Param("tableName") String tableName);

  Boolean dropTable(@Param("tableName") String tableName);

  List<PayBillsDebitDataResponse> getDebitData(@Param("filter") PayBillsJournalFilter filter);

  Boolean insertDebitData(@Param("payBillsDebitDataResponse") PayBillsDebitDataResponse payBillsDebitDataResponse, @Param("tableName") String tableName);

  Long getMainGlId(@Param("gldId") Long gldId);

  Boolean updateMainGlToGld(@Param("gldId") Long gldId);

  Boolean updateMainGl(@Param("gldId") Long gldId, @Param("mainGlId") Long mainGlId);

  Long getJournalClassId(@Param("gldId") Long gldId);

}