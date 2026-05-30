package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.PurchaseReceive;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.PurchaseReceiveFilter;
import com.ut.nlSystemAPi.model.response.PurchaseReceive.PurchaseReceiveDetailResponse;
import com.ut.nlSystemAPi.model.response.PurchaseReceive.PurchaseReceiveResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseReceiveMapper {

  List<PurchaseReceiveResponse> getList(@Param("filter") PurchaseReceiveFilter filter);

  List<PurchaseReceiveDetailResponse>getPurchaseReceiveDetail(@Param("purchaseOrderId") Long purchaseOrderId, @Param("purchaseReceiveResultId") Long purchaseReceiveResultId);

  List<PurchaseReceiveDetailResponse>getPurchaseReceiveDetailReceive(@Param("purchaseOrderId") Long purchaseOrderId);

  List<PurchaseReceiveDetailResponse>getPurchaseReceiveDetailRemain(@Param("purchaseOrderId") Long purchaseOrderId);

  List<PurchaseReceiveDetailResponse>getPurchaseReceiveDetailNotYetReceived(@Param("purchaseOrderId") Long purchaseOrderId);

  Long countList(@Param("filter") PurchaseReceiveFilter filter);

  List<PurchaseReceiveResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

  List<PurchaseReceiveResponse> getOneReceive(@Param("id") Long id, @Param("userId") Long userId);

  Boolean updatePurchaseOrder(@Param("id") Long id, @Param("status") Long status);

  Boolean insertPurchaseReceiveResults(@Param("purchaseReceive") PurchaseReceive purchaseReceive, @Param("code") String code, @Param("date") String date,@Param("id") Long id, @Param("userId") Long userId);

  Long getPurchaseReceiveResultId(@Param("purchaseOrderId")Long purchaseOrderId);

  Boolean insertPurchaseReceive(@Param("purchaseReceive")PurchaseReceive purchaseReceive);

  Boolean updateGroupTotalDetail(@Param("tableName")String tableName,@Param("purchaseReceive")PurchaseReceive purchaseReceive);

  Boolean insertGroupTotalDetail(@Param("tableName")String tableName,@Param("purchaseReceive")PurchaseReceive purchaseReceive);

  List<PurchaseReceiveDetailResponse> getPurchaseOrderDetail(@Param("id") Long id);

  String getLastCode();

  Long getTotalQtyReceive(@Param("productId") Long productId,@Param("purchaseOrderId") Long purchaseOrderId);

  Long getTotalQtyOrder(@Param("productId") Long productId, @Param("purchaseOrderId") Long purchaseOrderId);

  Long updateStatus(@Param("status") Long status, @Param("id") Long id);

  Long updateMessageId(@Param("messageId") Long messageId, @Param("id") Long id);

  Long getMessageId(@Param("id") Long purchaseReceiveId);

  String getCreatedBy(@Param("userId") Long userId);

  Long getQtyDoc(@Param("purchaseOrderId") Long purchaseOrderId, @Param("productId") Long productId, @Param("purchaseOrderDetailId") Long purchaseOrderDetailId);

  Boolean updateProduct(@Param("id") Long id, @Param("unitCost") Double unitCost);

}