package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ProfitLossReport;
import com.ut.nlSystemAPi.model.filter.ARDetailReportFilter;
import com.ut.nlSystemAPi.model.filter.AuditTrailReportFilter;
import com.ut.nlSystemAPi.model.filter.ProfitAndLossReportFilter;
import com.ut.nlSystemAPi.model.filter.generalLegerReportFilter;
import com.ut.nlSystemAPi.model.response.Report.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportBalanceSheetMapper {

    List<ProfitLossReportResponse> getListProfitLoss(@Param("filter") ProfitAndLossReportFilter filter);

    Boolean insertGeneralLedgerDetail(@Param("profitLoss") ProfitLossReport profitLossReport, @Param("tableName")  String tableName);

    Boolean insertGeneralLedgerDetailBatch(@Param("list") List<ProfitLossReport> profitLossReports, @Param("tableName") String tableName);

    List<chartAccountDetail> getChartAccountByGroupId(@Param("filter")ProfitAndLossReportFilter filter,@Param("groupId")  Long groupId);

    List<chartAccountDetail> getChartAccountCreditByGroupId(@Param("filter")ProfitAndLossReportFilter filter,@Param("groupId")  Long groupId);

    List<chartAccountDetail> getChartAccountByGroupIdAndParent(@Param("filter")ProfitAndLossReportFilter filter,@Param("groupId")  Long groupId);

    List<accountGroupDetail> getAccountGroupTotalEarningsBeforeInterestAndTax(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalCurrentAsset(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalOtherAsset(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalFixedAsset(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalAsset(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalEarningsBeforeTax(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalCurrentLiability(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalLongTermLiability(@Param("filter") ProfitAndLossReportFilter filter);

    List<accountGroupDetail> getAccountGroupTotalEquity(@Param("filter") ProfitAndLossReportFilter filter);

    List<ChartAccountSubDetail> getChartAccountBalanceSheetById(@Param("filter")ProfitAndLossReportFilter filter,@Param("chartAccountId") Long chartAccountId, @Param("date") String date);

    List<ChartAccountSubDetail> getChartAccountBalanceSheetPayableById(@Param("filter")ProfitAndLossReportFilter filter,@Param("chartAccountId") Long chartAccountId, @Param("date") String date);

    Boolean dropTable(@Param("tableName") String tableName);

//    Double  findGrandTotalAP(@Param("filter") ARDetailReportFilter filter);

//    Long countListTrailBalance(@Param("filter") generalLegerReportFilter filter);
}
