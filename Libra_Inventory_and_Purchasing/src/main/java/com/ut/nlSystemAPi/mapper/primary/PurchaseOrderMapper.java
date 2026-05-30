package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.filter.PurchaseOrderFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.filter.VendorFilter;
import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorPhoto;
import com.ut.nlSystemAPi.model.response.PurchaseOrder.PurchaseOrderDetailResponse;
import com.ut.nlSystemAPi.model.response.PurchaseOrder.PurchaseOrderResponse;
import com.ut.nlSystemAPi.model.response.PurchaseOrder.SoNoResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderMapper {

  List<PurchaseOrderResponse> getList(@Param("filter") PurchaseOrderFilter filter);

  Long countList(@Param("filter") PurchaseOrderFilter filter);

  List<PurchaseOrderResponse> getOne(@Param("id") Long id);

  VendorPhoto getRefDoc(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insertOrder(@Param("purchaseOrder") PurchaseOrderOrder purchaseOrder);

  Boolean deleteOrder(@Param("purchaseOrderId") Long purchaseOrderId);

  List<SoNoResponse> getOrder(@Param("id") Long id);

  Boolean insert(@Param("purchaseOrder") PurchaseOrder purchaseOrder);

  Boolean update(@Param("purchaseOrder") PurchaseOrder purchaseOrder);

  Boolean archive(@Param("id") Long id, @Param("userId") Long userId);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean closeStatus(@Param("filter") StatusFilter filter, @Param("userId") Long userId);

  String getLastCode();

  String getPrCode(@Param("id") Long id);

  Long getConversionByUomId(@Param("uomId") Long uomId);

  List<PurchaseOrderDetailResponse> getDetail(@Param("id") Long id);

  Long getSmallValUom(@Param("productId") Long productId);

  Boolean insertDetail(@Param("purchaseOrderDetail") PurchaseOrderDetail purchaseOrderDetail);

  Boolean deleteDetail(@Param("purchaseOrderId") Long purchaseOrderId);

  Boolean insertService(@Param("purchaseOrderServices") PurchaseOrderServices purchaseOrderServices);

  Boolean deleteService(@Param("purchaseOrderId") Long purchaseOrderId);

}