package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.JournalEntryDetail;
import com.ut.nlSystemAPi.model.ReceiveDetailPayment;
import com.ut.nlSystemAPi.model.ReceivePayment;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentPrintFilter;
import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePayment.ReceivePaymentDetailUpdateRequest;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentCreditStatementResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentDetailResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentFileResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivePaymentMapper {

  List<ReceivePaymentResponse> getList(@Param("filter") ReceivePaymentFilter filter);

  List<ReceivePaymentCreditStatementResponse> getCreditStatement(@Param("filter") ReceivePaymentFilter filter);

  Long countList(@Param("filter") ReceivePaymentFilter filter);

  List<ReceivePaymentResponse> getListPrint(@Param("filter") ReceivePaymentPrintFilter filter);

  List<ReceivePaymentDetailResponse> getReceivePaymentDetail(@Param("receivePaymentId") Long receivePaymentId);

  Long countListPrint(@Param("filter") ReceivePaymentPrintFilter filter);

  List<ReceivePaymentFileResponse> getGlFile(@Param("glId") Long glId);

  List<ReceivePaymentResponse> find(@Param("id") Long id);

  List<ReceivePaymentDetailResponse> getGeneralLedgerDetail(@Param("glId") Long glId);

  String getReLastReceivePaymentCode();

  String getReLastSalesOrderReceiptCode();

  Boolean insertReceivedPayment(@Param("receivePayment") ReceivePayment receivePayment);

  Boolean insertGeneralLedger(@Param("receivePayment") ReceivePayment receivePayment);

  Boolean insertReceivedPaymentDetail(@Param("receiveDetailPayment") ReceiveDetailPayment receiveDetailPayment);

  Boolean insertGeneralLedgerDetail(@Param("journalEntryDetail") JournalEntryDetail journalEntryDetail);

  Boolean updateSalesOrderBalance(@Param("request") ReceivePaymentDetailUpdateRequest request, @Param("userId") Long userId);

  Boolean insertSaleReceipt(@Param("receivePayment") ReceivePayment receivePayment);

  Long getChartAccountReceived(@Param("accountTypeId") Long accountTypeId);

}