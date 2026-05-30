package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.model.response.ExpiryDateReport.ExpiryDateReportResponse;
import com.ut.nlSystemAPi.model.response.GlobalInventory.GlobalInventoryReportDetailResponse;
import com.ut.nlSystemAPi.model.response.GlobalInventory.GlobalInventoryReportResponse;
import com.ut.nlSystemAPi.model.response.InventoryActivity.InventoryActivityReportDetailResponse;
import com.ut.nlSystemAPi.model.response.InventoryActivity.InventoryActivityReportResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport.InventoryAdjustmentDetailResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport.InventoryAdjustmentReportByItemDetailResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport.InventoryAdjustmentReportByItemResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport.InventoryAdjustmentReportResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportPriceRequestResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportQuotationResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportSaleOrderResponse;
import com.ut.nlSystemAPi.model.response.ProductAverageCost.ProductAverageCostReportResponse;
import com.ut.nlSystemAPi.model.response.ProductPriceList.ProductPriceListDetailReportResponse;
import com.ut.nlSystemAPi.model.response.ProductPriceList.ProductPriceListReportResponse;
import com.ut.nlSystemAPi.model.response.PurchasingReport.PurchaseByItemResponse;
import com.ut.nlSystemAPi.model.response.StockAvailableForSale.StockAvailableForSaleReportDetailResponse;
import com.ut.nlSystemAPi.model.response.StockAvailableForSale.StockAvailableForSaleReportResponse;
import com.ut.nlSystemAPi.model.response.Valuation.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryReportMapper {

  List<InventoryAdjustmentReportResponse> getListInventoryAdjustment(@Param("filter") InventoryAdjustmentReportFilter filter, @Param("userId") Long userId);

  Long countListInventoryAdjustment(@Param("filter") InventoryAdjustmentReportFilter filter, @Param("userId") Long userId);





  List<InventoryAdjustmentReportByItemResponse> getListInventoryAdjustmentByItemParentSummary(@Param("filter") InventoryAdjustmentReportByItemFilter filter, @Param("userId") Long userId);

  Double sumGrandTotalAdjustmentByItem(@Param("filter") InventoryAdjustmentReportByItemFilter filter, @Param("userId") Long userId);

  Long countListInventoryAdjustmentByItemParentSummary(@Param("filter") InventoryAdjustmentReportByItemFilter filter, @Param("userId") Long userId);



  List<InventoryAdjustmentReportByItemResponse> getListInventoryAdjustmentByItemDetail(@Param("filter") InventoryAdjustmentReportByItemFilter filter, @Param("userId") Long userId);

  List<InventoryAdjustmentReportByItemResponse> findAllAdjustmentByItemDetail(@Param("filter") InventoryAdjustmentReportByItemFilter filter, @Param("userId") Long userId);

  Long countListInventoryAdjustmentByItemDetail(@Param("filter") InventoryAdjustmentReportByItemFilter filter, @Param("userId") Long userId);

  List<InventoryAdjustmentReportByItemDetailResponse> getListInventoryAdjustmentByItemDetailDetail(@Param("productId") Long productId,  @Param("userId") Long userId);



  List<InventoryAdjustmentReportByItemResponse> getListInventoryAdjustmentByItemItemSummary(@Param("filter") InventoryAdjustmentReportByItemFilter filterByItem, @Param("userId") Long userId);

  List<InventoryAdjustmentReportByItemResponse> findAllAdjustmentByItemSummary(@Param("filter") InventoryAdjustmentReportByItemFilter filterByItem, @Param("userId") Long userId);

  List<InventoryAdjustmentReportByItemDetailResponse> getListInventoryAdjustmentByItemItemSummaryDetail(@Param("parentId") Long parentId, @Param("userId") Long userId);

  Long countListInventoryAdjustmentByItemItemSummary(@Param("filter") InventoryAdjustmentReportByItemFilter filterByItem, @Param("userId") Long userId);





  List<Long> countListStockAvailableForSale(@Param("filter") StockAvailableForSaleReportFilter filter);

  List<StockAvailableForSaleReportResponse> getListStockAvailableForSale(@Param("filter") StockAvailableForSaleReportFilter filter);

  List<StockAvailableForSaleReportDetailResponse> getListStockAvailableForSaleDetail(@Param("filter") StockAvailableForSaleReportFilter filter, @Param("tableName") String tableName, @Param("parentId") Long parentId);

  Double sumGrandTotalStockAvailableForSale(@Param("filter") StockAvailableForSaleReportFilter filter, @Param("tableName") String tableName);






  Long countListGlobalInventory(@Param("filter") GlobalInventoryReportFilter filter);

  List<GlobalInventoryReportResponse> getListGlobalInventory(@Param("filter") GlobalInventoryReportFilter filter);

  List<GlobalInventoryReportDetailResponse> getListGlobalInventoryDetail(@Param("filter") GlobalInventoryReportFilter filter, @Param("parentId") Long parentId);

  List<GlobalInventoryReportDetailResponse> getListGlobalInventoryDetailNoParent(@Param("filter") GlobalInventoryReportFilter filter);

  Double sumGrandTotalGlobalInventory(@Param("filter") GlobalInventoryReportFilter filter);




  Long countListProductAverageCost(@Param("filter") ProductAverageCostReportFilter filter);

  List<ProductAverageCostReportResponse> getListProductAverageCost(@Param("filter") ProductAverageCostReportFilter filter);





  Long countListProductPriceList(@Param("filter") ProductPriceListReportFilter filter, @Param("userId") Long userId);

  List<ProductPriceListReportResponse> getListProductPriceList(@Param("filter") ProductPriceListReportFilter filter, @Param("userId") Long userId);

  List<ProductPriceListDetailReportResponse> getListProductPriceListDetail(@Param("unitCost") Double unitCost, @Param("productId") Long productId, @Param("priceUomId") Long priceUomId);




//  Inventory Valuation

  Long countListValuationSummary(@Param("filter") ValuationReportFilter filter);

  List<ValuationReportResponse> getListValuationSummary(@Param("filter") ValuationReportFilter filter);

  Long countListValuationDetail(@Param("filter") ValuationReportFilter filter);

  List<ValuationReportResponse> getListValuationDetail(@Param("filter") ValuationReportFilter filter);

  List<ValuationReportDetailResponse> getListValuationDetailDetail(@Param("productId") Long productId);

  Double sumTotalQtyValuation(@Param("filter") ValuationReportFilter filter);

  Double sumTotalAssetValueValuation(@Param("filter") ValuationReportFilter filter);




//  Price Request Tracking

  Long countListPriceRequestTracking(@Param("filter") PriceRequestTrackingReportFilter filter);

  List<PriceRequestTrackingReportResponse> getListPriceRequestTracking(@Param("filter") PriceRequestTrackingReportFilter filter);

  List<PriceRequestTrackingReportPriceRequestResponse> getListPriceRequestTrackingPriceRequest(@Param("filter") PriceRequestTrackingReportFilter filter, @Param("createdBy") Long createdBy);

  List<PriceRequestTrackingReportQuotationResponse> getListPriceRequestTrackingQuotation(@Param("filter") PriceRequestTrackingReportFilter filter, @Param("createdBy") Long createdBy);

  List<PriceRequestTrackingReportSaleOrderResponse> getListPriceRequestTrackingSaleOrder(@Param("filter") PriceRequestTrackingReportFilter filter, @Param("createdBy") Long createdBy);




  // Inventory Activity

  List<InventoryActivityReportResponse> getListInventoryActivityParentSummary(@Param("filter") InventoryActivityReportFilter filter);

  List<InventoryActivityReportResponse> getListInventoryActivityParentSummaryNoParent(@Param("filter") InventoryActivityReportFilter filter);

  Long countListInventoryActivityParentSummary(@Param("filter") InventoryActivityReportFilter filter);





  List<InventoryActivityReportResponse> getListInventoryActivityItemSummary(@Param("filter") InventoryActivityReportFilter filter);

  List<InventoryActivityReportDetailResponse> getListInventoryActivityItemSummaryDetail(@Param("filter") InventoryActivityReportFilter filter, @Param("productId") Long productId);

  Long countListInventoryActivityItemSummary(@Param("filter") InventoryActivityReportFilter filter);





  List<InventoryActivityReportResponse> getListInventoryActivityItemActivitySummary(@Param("filter") InventoryActivityReportFilter filter);

  List<InventoryActivityReportDetailResponse> getListInventoryActivityItemActivitySummaryDetail(@Param("filter") InventoryActivityReportFilter filter, @Param("parentId") Long parentId);

  Long countListInventoryActivityItemActivitySummary(@Param("filter") InventoryActivityReportFilter filter);

  Double sumSubTotal(@Param("filter") InventoryActivityReportFilter filter, @Param("productId") Long productId);

  Double sumTotal(@Param("filter") InventoryActivityReportFilter filter);

  Double sumTotalActivity(@Param("filter") InventoryActivityReportFilter filter, @Param("type") Integer type);





  List<InventoryActivityReportResponse> getListInventoryActivityItemActivityDetail(@Param("filter") InventoryActivityReportFilter filter);

  List<InventoryActivityReportDetailResponse> getListInventoryActivityItemActivityDetailDetail(@Param("filter") InventoryActivityReportFilter filter, @Param("productId") Long productId);

  Long countListInventoryActivityItemActivityDetail(@Param("filter") InventoryActivityReportFilter filter);




  List<InventoryActivityReportResponse> getListInventoryActivityItemDetail(@Param("filter") InventoryActivityReportFilter filter);

  List<InventoryActivityReportDetailResponse> getListInventoryActivityItemDetailDetail(@Param("filter") InventoryActivityReportFilter filter, @Param("productId") Long productId);

  Long countListInventoryActivityItemDetail(@Param("filter") InventoryActivityReportFilter filter);



List<Long> getLocationsId(@Param("filter") InventoryActivityReportFilter filter);

List<InventoryActivityReportResponse> getItem(@Param("filter") InventoryActivityReportFilter filter);

List<Long> countItem(@Param("filter") InventoryActivityReportFilter filter);


List<InventoryActivityReportDetailResponse> getItemDetail(@Param("filter") InventoryActivityReportFilter filter, @Param("productId") Long productId);

List<InventoryActivityReportDetailResponse> getItemActiviesDetail(@Param("filter") InventoryActivityReportFilter filter, @Param("productId") Long productId, @Param("locationIds") List<Long> locationIds);

String getItemsId(@Param("filter") InventoryActivityReportFilter filter);





List<InventoryActivityReportResponse> getParentItem(@Param("filter") InventoryActivityReportFilter filter, @Param("productIds") String productIds);

List<InventoryActivityReportDetailResponse> getItemActiviesParentDetail(@Param("filter") InventoryActivityReportFilter filter, @Param("productId") Long productId, @Param("locationIds") List<Long> locationIds);

List<InventoryActivityReportDetailResponse> getItemSummaryDetail(@Param("filter") InventoryActivityReportFilter filter, @Param("productId") Long productId, @Param("locationIds") List<Long> locationIds);

List<InventoryActivityReportResponse> getParentItemQty(@Param("filter") InventoryActivityReportFilter filter, @Param("productIds") String productIds);

Double sumParentItemQty(@Param("filter") InventoryActivityReportFilter filter, @Param("productIds") String productIds);


//  Product Expiry Date

  Long countProductExpiryDate(@Param("filter") ProductExpiryDate filter, @Param("userId") Long userId);

  List<ExpiryDateReportResponse> getProductExpiryDate(@Param("filter") ProductExpiryDate filter, @Param("userId") Long userId);

}