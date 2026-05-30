package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.filter.PayBillsFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentOrganizationFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentPrintFilter;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsDebitDataResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentCreditResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentDetailResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentFileResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentOrganization.ReceivePaymentOrganizationResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivePaymentOrganizationMapper {

  List<ReceivePaymentOrganizationResponse> getList(@Param("filter") ReceivePaymentOrganizationFilter filter, @Param("tableName") String tableName);

  Long countList(@Param("filter") ReceivePaymentOrganizationFilter filter, @Param("tableName") String tableName);

  List<ReceivePaymentOrganizationResponse> getListPrint(@Param("filter") ReceivePaymentOrganizationFilter filter);

  String getReLastReceivePaymentCode();

  Boolean insertArAging(@Param("receivePayment") ReceivePaymentOrganization receivePayment);

  Boolean insertGeneralLedger(@Param("receivePayment") ReceivePaymentOrganization receivePayment);

  Boolean insertArAgingDetail(@Param("receiveDetailPayment") ReceivePaymentOrganizationDetail receiveDetailPayment);

  Boolean insertGeneralLedgerDetail(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  List<ReceivePaymentResponse> getListPrint(@Param("filter") ReceivePaymentPrintFilter filter);

  Long countListPrint(@Param("filter") ReceivePaymentPrintFilter filter);

  List<ReceivePaymentFileResponse> getGlFile(@Param("glId") Long glId);

  List<ReceivePaymentResponse> find(@Param("id") Long id);

  List<ReceivePaymentDetailResponse> getGeneralLedgerDetail(@Param("id") Long id);

  List<ReceivePaymentCreditResponse> getListCredit(@Param("filter") ReceivePaymentOrganizationFilter filter);

  Boolean createTable(@Param("tableName") String tableName);

  Boolean dropTable(@Param("tableName") String tableName);

  Boolean insertDebitData(@Param("response") ReceivePaymentCreditResponse response, @Param("tableName") String tableName);

  Long getMainGlId(@Param("gldId") Long gldId);

  Boolean updateMainGlToGld(@Param("gldId") Long gldId);

  Boolean updateMainGl(@Param("gldId") Long gldId, @Param("mainGlId") Long mainGlId);


}