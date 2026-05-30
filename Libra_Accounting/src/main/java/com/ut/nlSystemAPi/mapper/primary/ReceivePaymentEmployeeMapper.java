package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentEmployeeFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentOrganizationFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentPrintFilter;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsDebitDataResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentCreditResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentDetailResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentFileResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentEmployee.ReceivePaymentEmployeeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivePaymentEmployeeMapper {

  List<ReceivePaymentEmployeeResponse> getList(@Param("filter") ReceivePaymentEmployeeFilter filter, @Param("tableName") String tableName);

  Long countList(@Param("filter") ReceivePaymentEmployeeFilter filter, @Param("tableName") String tableName);

  List<ReceivePaymentEmployeeResponse> getListPrint(@Param("filter") ReceivePaymentEmployeeFilter filter);

  String getReLastReceivePaymentCode();

  Boolean insertArAging(@Param("receivePayment") ReceivePaymentEmployee receivePayment);

  Boolean insertGeneralLedger(@Param("receivePayment") ReceivePaymentEmployee receivePayment);

  Boolean insertArAgingDetail(@Param("receiveDetailPayment") ReceivePaymentEmployeeDetail receiveDetailPayment);

  Boolean insertGeneralLedgerDetail(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  List<ReceivePaymentResponse> getListPrint(@Param("filter") ReceivePaymentPrintFilter filter);

  Long countListPrint(@Param("filter") ReceivePaymentPrintFilter filter);

  List<ReceivePaymentFileResponse> getGlFile(@Param("glId") Long glId);

  List<ReceivePaymentResponse> find(@Param("id") Long id);

  List<ReceivePaymentDetailResponse> getGeneralLedgerDetail(@Param("id") Long id);

  Boolean createTable(@Param("tableName") String tableName);

  Boolean dropTable(@Param("tableName") String tableName);

  List<ReceivePaymentCreditResponse> getListGlData(@Param("filter") ReceivePaymentEmployeeFilter filter);

  Boolean insertDebitData(@Param("response") ReceivePaymentCreditResponse response, @Param("tableName") String tableName);

  Long getMainGlId(@Param("gldId") Long gldId);

  Boolean updateMainGlToGld(@Param("gldId") Long gldId);

  Boolean updateMainGl(@Param("gldId") Long gldId, @Param("mainGlId") Long mainGlId);


}