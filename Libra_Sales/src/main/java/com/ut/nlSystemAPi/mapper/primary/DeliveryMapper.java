package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Delivery.Delivery;
import com.ut.nlSystemAPi.model.entity.Delivery.DeliveryDetail;
import com.ut.nlSystemAPi.model.response.Delivery.DeliveryDetailResponse;
import com.ut.nlSystemAPi.model.response.Delivery.DeliveryResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryMapper {

    List<DeliveryResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<DeliveryResponse> getOne(@Param("id") Long id);

    List<DeliveryDetailResponse> getDetails(@Param("deliveryId") Long deliveryId);

    Boolean insert(@Param("delivery") Delivery delivery);

    Boolean insertDetail(@Param("detail") DeliveryDetail detail);

    Long getLocationHavingStock(@Param("warehouseId") Long warehouseId);

    Boolean updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("userId") Long userId);

    Boolean updateStockOrders(@Param("salesInvoiceId") Long salesInvoiceId, @Param("productId") Long productId, @Param("warehouseId") Long warehouseId);

    Boolean approve(@Param("id") Long id, @Param("userId") Long userId);

    Boolean updateSalesInvoice(@Param("id") Long id, @Param("deliveryId") Long deliveryId);

    Boolean updateStatusSalesInvoice(@Param("salesInvoiceId") Long salesInvoiceId);

    Long countItemOrder(@Param("saleOrderId") Long saleOrderId);

    Long countItemDelivery(@Param("saleOrderId") Long saleOrderId);

    Boolean updateStatusSalesOrder(@Param("saleOrderId") Long saleOrderId);
}
