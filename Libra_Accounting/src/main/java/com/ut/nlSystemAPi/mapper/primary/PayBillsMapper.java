package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.JournalEntry;
import com.ut.nlSystemAPi.model.JournalEntryDetail;
import com.ut.nlSystemAPi.model.PayBills;
import com.ut.nlSystemAPi.model.PayBillsDetail;
import com.ut.nlSystemAPi.model.filter.PayBillPrintFilter;
import com.ut.nlSystemAPi.model.filter.PayBillsFilter;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsDebitDataResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsPrintDetailResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsPrintResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentFileResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayBillsMapper {

  List<PayBillsResponse> getList(@Param("filter") PayBillsFilter filter, @Param("tableName") String tableName);

  Long countList(@Param("filter") PayBillsFilter filter);

  String getReLastPayBillCode();

  Boolean insertPayBill(@Param("payBills") PayBills payBills);

  Boolean insertPayBillDetail(@Param("payBillsDetail") PayBillsDetail payBillsDetail);

  Boolean insertLandedCost(@Param("payBillsDetail") PayBillsDetail payBillsDetail);

  Boolean insertLandedCostReceipt(@Param("payBillsDetail") PayBillsDetail payBillsDetail);

  Boolean insertPv(@Param("payBillsDetail") PayBillsDetail payBillsDetail);

  Boolean updatePurchaseOrder(@Param("id") Long id, @Param("balance") Double balance, @Param("userId") Long userId);

  Boolean updateERDepositAmount(@Param("payBillId") Long payBillId);

  List<PayBillsPrintResponse> getListPrint(@Param("filter") PayBillPrintFilter filter);

  Long countListPrint(@Param("filter") PayBillPrintFilter filter);

  List<ReceivePaymentFileResponse> getGlFile(@Param("glId") Long glId);

  List<PayBillsPrintResponse> find(@Param("id") Long id);

  List<PayBillsPrintDetailResponse> gePayBillNormalDetail(@Param("payBillId") Long payBillId);

  List<PayBillsPrintDetailResponse> getPayBillCheckDetail(@Param("glId") Long glId);

  Boolean insertGeneralLedger(@Param("journalEntry") JournalEntry journalEntry);
//
//  Boolean insertArAgingDetail(@Param("receiveDetailPayment") ReceivePaymentOrganizationDetail receiveDetailPayment);
//
  Boolean insertGeneralLedgerDetail(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  Boolean createTable(@Param("tableName") String tableName);

  Boolean dropTable(@Param("tableName") String tableName);

  List<PayBillsDebitDataResponse> getDebitData(@Param("filter") PayBillsFilter filter);

  Boolean insertDebitData(@Param("payBillsDebitDataResponse") PayBillsDebitDataResponse payBillsDebitDataResponse, @Param("tableName") String tableName);

  Long getGlId(@Param("gldId") Long gldId);

  Boolean updateMainGlByDedailtId(@Param("mainGlId") Long mainGlId, @Param("gldId") Long gldId);

  Boolean updateMainGl(@Param("gldId") Long gldId);

  Long getLocationGroupClassId(@Param("companyId") Long companyId, @Param("locationId") Long locationId);

  Long getJournalClassId(@Param("glId") Long glId);

  Long getChartAccountReceived(@Param("accountTypeId") Long accountTypeId);

}