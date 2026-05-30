package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.GoodReceiptNote;
import com.ut.nlSystemAPi.model.GoodReceiptNoteDetail;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.GoodReceiptNoteFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.response.GoodReceiptNote.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodReceiptNoteMapper {

  List<GoodReceiptNoteResponse> getList(@Param("filter") GoodReceiptNoteFilter filter, @Param("userId") Long userId);

  List<ListVendorResponse> getListVendor(@Param("filter") Filter filter);

  Long countList(@Param("filter") GoodReceiptNoteFilter filter, @Param("userId") Long userId);

  List<GoodReceiptNoteResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

  List<GoodReceiptNoteDetailResponse> getDetail(@Param("id") Long id);

  List<GoodReceiptNoteDetailResponse> getListReceiptDetail(@Param("id") Long id);

  Boolean insert(@Param("goodReceiptNote") GoodReceiptNote goodReceiptNote);

  Boolean insertReceiptNoteDetail(@Param("goodReceiptNoteDetail") GoodReceiptNoteDetail goodReceiptNoteDetail);

  Boolean update(@Param("goodReceiptNote") GoodReceiptNote goodReceiptNote);

  Boolean archive(@Param("id")  Long id,@Param("userId") Long userid);

  Boolean delete(@Param("id")  Long id,@Param("userId") Long userid);

  Boolean updateApprove(@Param("filter") StatusFilter statusFilter, @Param("userId") Long userid);

  Boolean deleteGoodReceiptNoteDetail(@Param("id")  Long id);

  String getCode(@Param("id") Long id);

  String getReLastReceivePaymentCode();

  Long getTotalQtyReceive(@Param("productId") Long productId,@Param("purchaseOrderId") Long purchaseOrderId);

  Boolean updateStatusPurchaseOrder( @Param("status") Long status, @Param("purchaseOrderId") Long purchaseOrderId);

}