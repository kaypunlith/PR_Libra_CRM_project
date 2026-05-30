package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ProfitLossDashboardReport;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.DashboardFilter;
import com.ut.nlSystemAPi.model.response.DashboradAccounting.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardAccountingMapper {

    List<ProfileAndLostDashboardResponse> getList(@Param("filter") DashboardFilter filter);

    List<RevenueDashboardResponse> getListRevenue(@Param("filter") DashboardFilter filter);

    List<ExpenditureDashboardResponse> getListExpenditure(@Param("filter") DashboardFilter filter);

    List<CustomerSegmentDashboardResponse> getListCustomerSegmentation(@Param("filter") DashboardFilter filter);

    List<CustomerSegmentDashboardResponse> getListSaleTopCustomer(@Param("filter") DashboardFilter filter);

    // Get Invoice
    List<ExpenditureDashboardResponse> getListInvoice(@Param("filter") DashboardFilter filter);

    List<ExpenditureDashboardResponse> getListVoidInvoice(@Param("filter") DashboardFilter filter);

    List<ExpenditureDashboardResponse> getListCmInvoice(@Param("filter") DashboardFilter filter);

    List<ExpenditureDashboardResponse> getListDiscountInvoice(@Param("filter") DashboardFilter filter);

    List<InvoiceCurrenciesDashboardResponse> getListSaleTopItem(@Param("filter") DashboardFilter filter);

    List<InvoiceCurrenciesDashboardResponse> getInvoiceCurrencies(@Param("filter") DashboardFilter filter);

    List<SaleByproductGroupDashboardResponse> getListSaleByProductGroup(@Param("filter") DashboardFilter filter);

    List<SaleYearToDateDashboardResponse> getListSaleYearToDate(@Param("filter") DashboardFilter filter);

    List<YearToDateDetailResponse> getListSaleYearToDateDetail(@Param("filter") DashboardFilter filter);

    List<PurchaseTopVendorResponse> getListPurchaseToVendor(@Param("filter") DashboardFilter filter);

    List<PurchaseCurrenciesResponse> getListPurchaseCurrencies(@Param("filter") DashboardFilter filter);

    List<PurchaseYearToDateResponse> getPurchaseYearToDate(@Param("filter") DashboardFilter filter);

    List<PurchaseYearToDateDetailResponse> getPurchaseYearToDateDetail(@Param("filter") DashboardFilter filter);

    List<SaleTargetVsActualResponse> getSaleTargetVsActual(@Param("filter") DashboardFilter filter);

    List<SaleTargetResponse> getListTarget(@Param("filter") DashboardFilter filter);

    List<GrowthRateDashboardResponse> getListGrowthRate(@Param("filter") DashboardFilter filter);

    List<GrowthRateDetailDashboardResponse> getListGrowthRateDetail(@Param("filter") DashboardFilter filter);


    List<TotalSaleByQuarterDashboardResponse> getListTotalSaleByQuarter(@Param("filter") DashboardFilter filter);


    List<TotalSaleByQuarterDetailDashboardResponse> getListDetailSaleByQuarter(@Param("filter") DashboardFilter filter,@Param("monthFrom") String monthFrom, @Param("monthTo") String monthTo);

    List<GraphCostOfGoodsRaw> getListGraph(@Param("filter") DashboardFilter filter);

//    AP
    List<AgingReportResult> getListAmountOutStandingFirstAP(@Param("filter") DashboardFilter filter);

    List<AgingReportResult> getListAmountOutStandingSecondAP(@Param("filter") DashboardFilter filter,@Param("purchaseOrderId") String purchaseOrderId,@Param("arrMainGlId") String arrMainGlId);

    List<AgingReportResult> getListAmountOutStandingThirdAP(@Param("filter") DashboardFilter filter);

    List<AgingReportResult> getListAmountOutStandingFourthAP(@Param("filter") DashboardFilter filter,@Param("purchaseReturnId") String purchaseReturnId);

    List<AgingReportResult> getAmountFirstOverdueAP(@Param("filter") DashboardFilter filter);

    List<AgingReportResult> getAmountSecondOverdueAP(@Param("filter") DashboardFilter filter,@Param("purchaseOrderId") String purchaseOrderId,@Param("arrMainGlId") String arrMainGlId);

    List<AgingReportResult> getAmountThirdOverDueAP(@Param("filter") DashboardFilter filter);

    List<AgingReportResult> getAmountFourthOverDueAP(@Param("filter") DashboardFilter filter,@Param("purchaseReturnId") String purchaseReturnId,@Param("arrGlIdThird") String arrGlIdThird);
// AR
    List<AgingReportResult> getLisAmountFirstOutstandingAR(@Param("filter") DashboardFilter filter);

    List<AgingReportResult> getAmountSecondOutstandingAR(@Param("filter") DashboardFilter filter,@Param("saleOrderId") String saleOrderId,@Param("arrGlId") String arrGlId);

    List<AgingReportResult> getAmountThirdOutStandingAR(@Param("filter") DashboardFilter filter,@Param("saleOrderId") String saleOrderId);

    List<AgingReportResult> getAmountFourthOutStandingAR(@Param("filter") DashboardFilter filter,@Param("creditMemo") String creditMemo);

    List<AgingReportResult> getAmountFirstOverdueAR(@Param("filter") DashboardFilter filter);

    List<AgingReportResult> getAmountSecondOverdueAR(@Param("filter") DashboardFilter filter,@Param("saleOrderId") String saleOrderId,@Param("arrGlIdAr") String arrGlIdAr);

    List<AgingReportResult> getAmountThirdOverDueAR(@Param("filter") DashboardFilter filter,@Param("saleOrderId") String saleOrderId);

    List<AgingReportResult> getAmountFourthOverDueAR(@Param("filter") DashboardFilter filter,@Param("creditMemo") String creditMemo);
    //graphic  AP

    List<GraphicNetDayResponse> getListNetDayGraphic(@Param("filter") DashboardFilter filter);

    List<GraphicResponseAP> getLisAmountGraphicFirstOutStanding(@Param("filter") DashboardFilter filter, @Param("netDay") Long netDay);

    List<GraphicResponseAP> getLisAmountGraphicSecondOutStanding(@Param("filter") DashboardFilter filter, @Param("purchaseOrderId") String purchaseOrderId, @Param("arrMainGlId") String arrMainGlId, @Param("netDay") Long netDay);

    List<GraphicResponseAP> getLisAmountGraphicThirdOutStanding(@Param("filter") DashboardFilter filter, @Param("netDay") Long netDay);

    List<GraphicResponseAP> getLisAmountGraphicFourthOutStanding(@Param("filter") DashboardFilter filter, @Param("purchaseReturnId") String purchaseReturnId, @Param("netDay") Long netDay);


    List<GraphicResponseAP> getAmountGraphicFirstOverdue(@Param("filter") DashboardFilter filter, @Param("netDay") Long netDay);


    List<GraphicResponseAP> getAmountGraphicSecondOverdue(@Param("filter") DashboardFilter filter, @Param("purchaseOrderId") String purchaseOrderId, @Param("arrMainGlId") String arrMainGlId, @Param("netDay") Long netDay);

    List<GraphicResponseAP> getAmountGraphicThirdOverdue(@Param("filter") DashboardFilter filter, @Param("netDay") Long netDay);


    List<GraphicResponseAP> getAmountGraphicFourthOverdue(@Param("filter") DashboardFilter filter, @Param("purchaseReturnId") String purchaseReturnId, @Param("netDay") Long netDay);

    //graphic  AP

    List<GraphicResponseAR> getLisAmountGraphicFirstOutStandingAR(@Param("filter") DashboardFilter filter, @Param("netDay") Long netDay);


    List<GraphicResponseAR> getLisAmountGraphicSecondOutStandingAR(@Param("filter") DashboardFilter filter, @Param("saleOrderId") String saleOrderId,@Param("arrMainGlId") String arrMainGlId,@Param("netDay") Long netDay);


    List<GraphicResponseAR> getLisAmountGraphicThirdOutStandingAR(@Param("filter") DashboardFilter filter, @Param("saleOrderId") String saleOrderId);

    List<GraphicResponseAR> getLisAmountGraphicFourthOutStandingAR(@Param("filter") DashboardFilter filter, @Param("saleOrderId") String creditMemo);


    List<GraphicResponseAR> getAmountGraphicFirstOverdueAR(@Param("filter") DashboardFilter filter, @Param("netDay") Long netDay );

    List<GraphicResponseAR> getAmountGraphicSecondOverdueAR(@Param("filter") DashboardFilter filter, @Param("saleOrderId") String saleOrderId,@Param("arrMainGlId") String arrMainGlId,@Param("netDay") Long netDay);

    List<GraphicResponseAR> getAmountGraphicThirdOverdueAR(@Param("filter") DashboardFilter filter, @Param("saleOrderId") String saleOrderId);

    List<GraphicResponseAR> getAmountGraphicFourthOverdueAR(@Param("filter") DashboardFilter filter, @Param("creditMemo") String creditMemo);

    Boolean dropTable(@Param("tableName") String tableName);

    Boolean insertGeneralLedgerDetail(@Param("profitLossDashboardReport") ProfitLossDashboardReport profitLossDashboardReport, @Param("tableName")  String tableName);

    Long countList(@Param("filter") Filter filter);

    List<ListProfitAndLostResponse> getListProfitAndLost(@Param("filter") DashboardFilter filter, @Param("tableName") String tableName);

    Boolean createTable(@Param("tableName") String tableName);
}
