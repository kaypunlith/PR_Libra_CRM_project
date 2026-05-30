package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.PriceRequest;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.PriceRequestFilter;
import com.ut.nlSystemAPi.model.request.Login.PriceRequest.PriceRequestApproveRequest;
import com.ut.nlSystemAPi.model.request.Login.PriceRequest.PriceRequestStatusUpdate;
import com.ut.nlSystemAPi.model.response.PriceRequest.PriceRequestApproveResponse;
import com.ut.nlSystemAPi.model.response.PriceRequest.PriceRequestResponse;
import com.ut.nlSystemAPi.model.response.PriceRequest.PriceRequestResponseDetail;
import com.ut.nlSystemAPi.model.response.PriceRequest.PriceRequestResponseSummary;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportQuotationResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportSaleOrderResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRequestMapper {

    List<PriceRequestResponse> getList(@Param("filter") PriceRequestFilter filter, @Param("userId") Long userId);

    List<PriceRequestTrackingReportQuotationResponse> getListQuotation(@Param("productId") Long productId);

    List<PriceRequestTrackingReportSaleOrderResponse> getListSaleOrder(@Param("productId") Long productId);

    Long countList(@Param("filter") PriceRequestFilter filter, @Param("userId") Long userId);

    String getLastCode();

    Long countListSummary(@Param("filter") PriceRequestFilter filter);

    List<PriceRequestResponseDetail> getOne(@Param("id") Long id);

    PriceRequest getDataSendToTelegram(@Param("id") Long id);

    Boolean insertPriceRequestMobile(@Param("id") Long id, @Param("userId") Long userId);

    List<PriceRequestResponseDetail> print(@Param("customerId") Long customerId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("priceRequest") PriceRequest priceRequest);

    Boolean delete(@Param("id") Long id, @Param("modifiedBy") Long modifiedBy);

    Boolean update(@Param("priceRequest") PriceRequest priceRequest);

    Boolean updateStatus(@Param("statusUpdate") PriceRequestStatusUpdate statusUpdate);

    String getFullName(@Param("userId") Long userId);

    List<PriceRequestResponseSummary> getListSummary(@Param("filter") PriceRequestFilter filter);

    Boolean updateIsConvert(@Param("userId") Long userId, @Param("productId") Long productId, @Param("priceRequestId") Long priceRequestId);

    List<PriceRequestApproveResponse> getListApprove(@Param("filter") Filter filter);

    Long countListApprove(@Param("filter") Filter filter);

    Boolean approve(@Param("request") PriceRequestApproveRequest request, @Param("userId") Long userId);

    Long getMessageId(@Param("id") Long id);

    Long updateMessageId(@Param("id") Long id, @Param("messageId") Long messageId);

    Long sumListApprove();

}