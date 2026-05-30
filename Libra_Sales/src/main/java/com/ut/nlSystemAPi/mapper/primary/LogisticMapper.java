package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Logistic.LogisticReceive;
import com.ut.nlSystemAPi.model.entity.Logistic.LogisticReceiveResult;
import com.ut.nlSystemAPi.model.entity.Logistic.Logistics;
import com.ut.nlSystemAPi.model.filter.LogisticFilter;
import com.ut.nlSystemAPi.model.response.Logistic.LogisticDetailResponse;
import com.ut.nlSystemAPi.model.response.Logistic.LogisticResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticMapper {

  List<LogisticResponse> getList(@Param("filter") LogisticFilter filter);

  Long countList(@Param("filter") LogisticFilter filter);

  List<LogisticResponse> getOne(@Param("id") Long id);

  List<Long> getSalesInvoiceIds(@Param("logisticId") Long logisticId);

  List<Long> getSalesInvoiceDetailIds(@Param("saleInvoiceIds") List<Long> saleInvoiceIds);

  Boolean insert(@Param("logistic") Logistics logistic);

  Boolean insertDetail(@Param("logisticId") Long logisticId, @Param("salesInvoiceId") Long salesInvoiceId);

  List<LogisticResponse> getOneDelivery(@Param("id") Long id, @Param("type") Long type);

  List<LogisticDetailResponse> getDetailDelivered(@Param("salesInvoiceIds") List<Long> salesInvoiceIds);

  List<LogisticDetailResponse> getDetailRemaining(@Param("salesInvoiceIds") List<Long> salesInvoiceIds);

  List<LogisticDetailResponse> getDetailNotYetDelivery(@Param("salesInvoiceIds") List<Long> salesInvoiceIds);

  Boolean insertReceiveResult(@Param("logisticReceiveResult") LogisticReceiveResult logisticReceiveResult);

  Boolean insertReceive(@Param("logisticReceive") LogisticReceive logisticReceive);

  Long getTotalQtyDelivery(@Param("itemId") Long productId,@Param("salesInvoiceId") String salesInvoiceId);

  Long getTotalQtyOrder(@Param("itemId") Long productId, @Param("salesInvoiceIds") List<Long> salesInvoiceIds);

  Boolean updateStatus(@Param("status") Integer status, @Param("salesInvoiceIds") List<Long> salesInvoiceIds);

  Boolean updateStatusLogistic(@Param("status") Integer status, @Param("logisticId") Long logisticId);

}