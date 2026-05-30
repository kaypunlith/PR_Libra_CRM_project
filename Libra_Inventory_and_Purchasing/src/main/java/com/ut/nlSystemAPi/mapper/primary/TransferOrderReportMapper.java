package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.filter.TransferOrderReportByItemFilter;
import com.ut.nlSystemAPi.model.filter.TransferOrderReportFilter;
import com.ut.nlSystemAPi.model.response.TransferOrderReport.TransferOrderReportByItemResponse;
import com.ut.nlSystemAPi.model.response.TransferOrderReport.TransferOrderReportByItemDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferOrderReport.TransferOrderReportResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferOrderReportMapper {

  List<TransferOrderReportResponse> getList(@Param("filter") TransferOrderReportFilter filter);

  Long countList(@Param("filter") TransferOrderReportFilter filter);



  Long countListByItemSummary(@Param("filterByItem") TransferOrderReportByItemFilter filterByItem);

  List<TransferOrderReportByItemResponse> getListByItemSummary(@Param("filterByItem") TransferOrderReportByItemFilter filterByItem);

  List<TransferOrderReportByItemDetailResponse> getListByItemSummaryDetail(@Param("parentId") Long parentId, @Param("filterByItem") TransferOrderReportByItemFilter filterByItem);






  List<Long> countListByItemDetail(@Param("filterByItem") TransferOrderReportByItemFilter filterByItem);

  List<TransferOrderReportByItemResponse> getListByItemDetail(@Param("filterByItem") TransferOrderReportByItemFilter filterByItem);

  List<TransferOrderReportByItemDetailResponse> getListByItemDetailDetail(@Param("parentId") Long parentId, @Param("filterByItem") TransferOrderReportByItemFilter filterByItem);

  Double getSubTotalCost(@Param("parentId") Long parentId, @Param("filterByItem") TransferOrderReportByItemFilter filterByItem);

  TransferOrderReportByItemDetailResponse getGrandTotalCost(@Param("filterByItem") TransferOrderReportByItemFilter filterByItem);






  List<TransferOrderReportByItemResponse> getListByItemParentSummary(@Param("filterByItem") TransferOrderReportByItemFilter filterByItem);

  Long countListByParentSummary(@Param("filterByItem") TransferOrderReportByItemFilter filterByItem);

  Double sumGrandTotalParentSummary(@Param("filterByItem") TransferOrderReportByItemFilter filterByItem);

}