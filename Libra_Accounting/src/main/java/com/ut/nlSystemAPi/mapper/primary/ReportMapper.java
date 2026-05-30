package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ProfitLossReport;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.model.response.Report.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportMapper {

    List<GeneralLedgerReportResponse> getChartAccountGl(@Param("filter") generalLegerReportFilter filter);

    List<GeneralLedgerReportResponse> getListGeneralLegerReport(@Param("filter") generalLegerReportFilter filter, @Param("chartAccountId") Long chartAccountId);

    List<Long> countList(@Param("filter") generalLegerReportFilter filter);

    List<GeneralLedgerReportResponse> getListJournal(@Param("filter") generalLegerReportFilter filter);

    Long countListJournal(@Param("filter") generalLegerReportFilter filter);

    List<TrialBalanceResponse> getListTrailBalance(@Param("filter") generalLegerReportFilter filter);

    List<TrialBalanceDetails> getTrialBalanceDetails(@Param("filter") generalLegerReportFilter filter,@Param("chartAccountId") Long chartAccountId, @Param("months") String months);

    Long countListTrailBalance(@Param("filter") generalLegerReportFilter filter);

    List<CheckReportResponse> getListCheck(@Param("filter") generalLegerReportFilter filter);

    List<CheckReportResponse> getListCheckDetail(@Param("journalEntryId") Long journalEntryId);

    List<CheckReportResponse> getListCheckGrandTotal(@Param("filter") generalLegerReportFilter filter);

    Long countListCheck(@Param("filter") generalLegerReportFilter filter);

    List<CheckReportResponse> getListDebit(@Param("filter") generalLegerReportFilter filter);

    List<CheckReportResponse> getListDebitDetail(@Param("journalEntryId") Long journalEntryId);

    Long countListDebit(@Param("filter") generalLegerReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByQuotation(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailBySaleInvoice(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailBySaleOrder(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByPOS(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByCreditMemo(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByDelivery(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByReceivePayment(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByAdjustment(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByTransferOrder(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByPurchaseOrder(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByPurchaseBill(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByPurchaseReceive(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByBill(@Param("filter") AuditTrailReportFilter filter);

    List<AuditTrailReportResponse> getListAuditTrailByBillReturn(@Param("filter") AuditTrailReportFilter filter);

//    Long countListAR(@Param("filter") AuditTrailReportFilter filter);

    List<ARListDetailReportResponse> getListARDetail(@Param("filter") ARDetailReportFilter filter,@Param("fromValue") Long fromValue,@Param("toValue") Long toValue);

    List<ARListDetailReportResponse> getListARDetailAll(@Param("filter") ARDetailReportFilter filter,@Param("toValue") Long toValue);

    List<ARListDetailReportResponse> getListARDetailByTermRange(@Param("filter") ARDetailReportFilter filter, @Param("fromNetDay") Long fromNetDay, @Param("toNetDay") Long toNetDay);

    List<ApListDetailReportResponse> getListAPDetailByTermRange(@Param("filter") ARDetailReportFilter filter, @Param("fromNetDay") Long fromNetDay, @Param("toNetDay") Long toNetDay);


    List<ARListDetailReportResponse> getARGraphDetail(@Param("filter") ARDetailReportFilter filter);


    List<ApListDetailReportResponse> getAPGraphDetail(@Param("filter") ARDetailReportFilter filter);


    List<ApListDetailReportResponse> getListAPDetail(@Param("filter") ARDetailReportFilter filter, @Param("fromValue") Long fromValue, @Param("toValue") Long toValue);

    List<ApListDetailReportResponse> getListAPDetailAll(@Param("filter") ARDetailReportFilter filter,@Param("toValue") Long toValue);

   Double  findGrandTotalAR(@Param("filter") ARDetailReportFilter filter);

    Boolean createTable(@Param("tableName") String tableName);

    List<ProfitLossReportResponse> getListProfitLoss(@Param("filter") ProfitAndLossReportFilter filter);

    Boolean insertGeneralLedgerDetail(@Param("profitLoss") ProfitLossReport profitLossReport, @Param("tableName")  String tableName);

    List<accountGroupDetail> getAccountGroupDetailIncome(@Param("filter") ProfitAndLossReportFilter filter);

    List<chartAccountDetail> getChartAccountByGroupId(@Param("filter")ProfitAndLossReportFilter filter,@Param("groupId")  Long groupId);

    List<ChartAccountSubDetail> getChartAccountById(@Param("filter")ProfitAndLossReportFilter filter,@Param("chartAccountId") Long chartAccountId);

    List<ChartAccountSubDetail> getChartAccountRevenueById(@Param("filter")ProfitAndLossReportFilter filter,@Param("chartAccountId") Long chartAccountId);

    List<ChartAccountSubDetail> getBalanceByColumn(@Param("filter")ProfitAndLossReportFilter filter, @Param("chartAccountGroupId") String chartAccountGroupId);


    List<chartAccountDetail> getChartAccountCostByGroupId(@Param("filter")ProfitAndLossReportFilter filter,@Param("groupId")  Long groupId);


    List<chartAccountDetail> getChartAccountByGroupIdAndParent(@Param("groupId")  Long groupId);

    List<accountGroupDetail> getAccountGroupDetailCostOfGoodsSold(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupDetailGrossProfit(@Param("filter") ProfitAndLossReportFilter filter);


    List<accountGroupDetail> getAccountGroupDetailNetOrdinaryIncome(@Param("filter") ProfitAndLossReportFilter filter);


    List<accountGroupDetail> getAccountGroupTotalOtherRevenue(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalOtherExpend(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalEarningsBeforeInterestAndTax(@Param("filter") ProfitAndLossReportFilter filter);


    List<accountGroupDetail> getAccountGroupTotalCurrentAsset(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalFixedAsset(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalAsset(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalEarningsBeforeTax(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalTaxExpend(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalCurrentLiability(@Param("filter") ProfitAndLossReportFilter filter);


    List<accountGroupDetail> getAccountGroupTotalLongTermLiability(@Param("filter") ProfitAndLossReportFilter filter);

    Boolean dropTable(@Param("tableName")  String tableName);

    List<CashFlowGroupResponse> getListCashFlowOperating(@Param("filter") cashFlowReportFilter filter);

    Double getNetInCom(@Param("filter") cashFlowReportFilter filter);

    List<ChartAccountSubDetail> getNetInComeByMonth(@Param("filter") cashFlowReportFilter filter);

    List<ChartAccountResponse> getListChartAccount(@Param("groupId")Long groupId,@Param("filter") cashFlowReportFilter filter);

    List<ChartAccountResponse> getListChartAccountCashBank(@Param("groupId")Long groupId,@Param("filter") cashFlowReportFilter filter);

    List<ChartAccountResponse> getListChartAccountCashBankPeriod(@Param("groupId")Long groupId,@Param("filter") cashFlowReportFilter filter);

    List<ChartAccountResponse> getListChartAccountDebit(@Param("groupId") Long groupId, @Param("filter") cashFlowReportFilter filter);

    List<ChartAccountResponse> getListChartAccountCredit(@Param("groupId")Long groupId,@Param("filter") cashFlowReportFilter filter);

    List<ChartAccountResponse> getListOperationChartAccount(@Param("groupId")Long groupId,@Param("filter") cashFlowReportFilter filter);

    List<CashFlowGroupResponse> getListCashFlowInvesting(@Param("filter") cashFlowReportFilter filter);

    List<CashFlowGroupResponse> getListCashFlowFinancing(@Param("filter") cashFlowReportFilter filter);

    List<CashFlowGroupResponse> getListCashFlowCashBank(@Param("filter") cashFlowReportFilter filter);

    List<CashFlowGroupResponse> getListTotalPerStatement(@Param("filter") cashFlowReportFilter filter);

    List<ChartAccountSubDetail> getChartAccountCashFlowMonthById(@Param("filter") cashFlowReportFilter filter,@Param("chartAccountId") Long chartAccountId, @Param("date") String date);

    List<ChartAccountSubDetail> getChartAccountCashFlowMonthStatementById(@Param("filter") cashFlowReportFilter filter,@Param("chartAccountId") Long chartAccountId, @Param("date") String date);

    List<ChartAccountSubDetail> getChartAccountCashFlowMonthStatementByChartAccountId(@Param("filter") cashFlowReportFilter filter,@Param("chartAccountId") Long chartAccountId, @Param("date") String date);




    Double findBeginningBalanceAmount(@Param("filter") ReconcileReportFilter filter);

    List<ReconcileCheckPaymentDetail> findReconcileCheckPaymentDetails(@Param("filter") ReconcileReportFilter filter);

    List<ReconcileDepositsCreditsDetail> findReconcileDepositsCreditsDetails(@Param("filter") ReconcileReportFilter filter);

    List<ReconcileCheckPaymentTransactionDetail> findReconcileUnclearedTransactionsDetails(@Param("filter") ReconcileReportFilter filter);

    List<ReconcileCheckPaymentTransactionDetail> findReconcileCheckPaymentTransactionsDetails(@Param("filter") ReconcileReportFilter filter);

    List<ReconcileDepositsCreditTransactionDetail> findReconcileDepositsCreditsTransactionsDetails(@Param("filter") ReconcileReportFilter filter);

//    Double  findGrandTotalAP(@Param("filter") ARDetailReportFilter filter);

//    Long countListTrailBalance(@Param("filter") generalLegerReportFilter filter);
}
