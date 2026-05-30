package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.entity.SaleOrder.*;
import com.ut.nlSystemAPi.model.filter.SaleOrderFilter;
import com.ut.nlSystemAPi.model.response.Quotation.QuotationCreator;
import com.ut.nlSystemAPi.model.response.SaleOrder.SaleOrderDetailResponse;
import com.ut.nlSystemAPi.model.response.SaleOrder.SaleOrderQuotationResponse;
import com.ut.nlSystemAPi.model.response.SaleOrder.SaleOrderResponse;
import com.ut.nlSystemAPi.model.response.TermCondition.TermConditionResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleOrderMapper {

  List<SaleOrderResponse> getList(@Param("filter") SaleOrderFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") SaleOrderFilter filter, @Param("userId") Long userId);

  List<SaleOrderResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

  Boolean insert(@Param("saleOrder") SaleOrder saleOrder);

  Boolean insertSaleOrderQuotation(@Param("saleOrderId") Long saleOrderId, @Param("quotationId") Long quotationId);

  List<SaleOrderQuotationResponse> getSaleOrderQuotation(@Param("saleOrderId") Long saleOrderId);

  Boolean insertTermCondition(@Param("termCondition") SaleOrderTermCondition termCondition);

  List<TermConditionResponse> getTermCondition(@Param("saleOrderId") Long saleOrderId);

  Boolean insertDetail(@Param("detail") SaleOrderDetail detail);

  Boolean insertService(@Param("service") SaleOrderServices service);

  Boolean insertMisc(@Param("misc") SaleOrderMisc misc);

  List<SaleOrderDetailResponse> getListDetail(@Param("saleOrderId") Long saleOrderId, @Param("warehouseId") Long warehouseId);

  Boolean approve(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

  Boolean close(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

  QuotationCreator getCreator(@Param("id") Long id);

  Long countItems(@Param("saleOrderId") Long saleOrderId);

  Long countEdit(@Param("saleOrderCode") String saleOrderCode);

  Long checkDelivery(@Param("saleOrderId") Long saleOrderId);

  Long checkInvoice(@Param("saleOrderId") Long saleOrderId);

  Long checkCatalogOrder(@Param("saleOrderId") Long saleOrderId);

}