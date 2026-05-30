package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.model.response.PurchasingReport.*;
import com.ut.nlSystemAPi.model.response.TransferOrderReport.TransferOrderReportByItemResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasingReportMapper {

  List<PurchaseBillBarcodeResponse> getList(@Param("filter") PurchasingReportFilter filter);

  Long countList(@Param("filter") PurchasingReportFilter filter);

  List<PurchaseBillBarcodeDetailResponse> purchaseBillBarcodeDetail (@Param("id") Long id);





  Long countListInvoicePurchaseBill(@Param("filter") InvoicePurchaseBillReportFilter filter);

  List<InvoicePurchaseBillResponse> getListInvoicePurchaseBill(@Param("filter") InvoicePurchaseBillReportFilter filter);





  List<InvoicePurchaseBillReturnResponse> getListInvoicePurchaseBillReturn(@Param("filter") InvoicePurchaseBillReturnReportFilter filter);

  Long countListInvoicePurchaseBillReturn(@Param("filter") InvoicePurchaseBillReturnReportFilter filter);






  List<PurchaseByItemResponse> getListParentSummaryProduct(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalParentSummaryProduct(@Param("filter") PurchaseByItemFilter filter);

  List<PurchaseByItemResponse> getListParentSummaryService(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalParentSummaryService(@Param("filter") PurchaseByItemFilter filter);

  List<PurchaseByItemResponse> getListParentSummaryMiscellaneous(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalParentSummaryMiscellaneous(@Param("filter") PurchaseByItemFilter filter);

  Long countListParentSummary(@Param("filter") PurchaseByItemFilter filter);



  List<PurchaseByItemResponse> getListItemSummaryProduct(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalQtyItemSummaryProduct(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalItemSummaryProduct(@Param("filter") PurchaseByItemFilter filter);

  List<PurchaseByItemResponse> getListItemSummaryService(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalItemSummaryService(@Param("filter") PurchaseByItemFilter filter);

  List<PurchaseByItemResponse> getListItemSummaryMiscellaneous(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalItemSummaryMiscellaneous(@Param("filter") PurchaseByItemFilter filter);

  Long countListItemSummary(@Param("filter") PurchaseByItemFilter filter);




  List<PurchaseByItemResponse> getListItemDetailProduct(@Param("filter") PurchaseByItemFilter filter);

  List<PurchaseByItemDetailResponse> getListItemDetailProductDetail(@Param("filter") PurchaseByItemFilter filter, @Param("parentId") Long parentId);

  Double sumTotalQtyItemDetailProduct(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalItemDetailProduct(@Param("filter") PurchaseByItemFilter filter);

  List<PurchaseByItemResponse> getListItemDetailService(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalItemDetailService(@Param("filter") PurchaseByItemFilter filter);

  List<PurchaseByItemResponse> getListItemDetailMiscellaneous(@Param("filter") PurchaseByItemFilter filter);

  Double sumTotalItemDetailMiscellaneous(@Param("filter") PurchaseByItemFilter filter);

  Long countListItemDetailPurchaseByItem(@Param("filter") PurchaseByItemFilter filter);






  List<PayBillReportResponse> getListPayBill(@Param("filter") PayBillReportFilter filter, @Param("userId") Long userId);

  List<PayBillReportDetailResponse> getListPayBillDetail(@Param("filter") PayBillReportFilter filter, @Param("id") Long vendorId, @Param("userId") Long userId);

  Long countListPayBill(@Param("filter") PayBillReportFilter filter, @Param("userId") Long userId);

  Double sumGrandTotalPayBill(@Param("filter") PayBillReportFilter filter, @Param("userId") Long userId);

}