package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.StockOrder;
import com.ut.nlSystemAPi.model.entity.CreditMemo.CreditMemo;
import com.ut.nlSystemAPi.model.entity.CreditMemo.CreditMemoReceipt;
import com.ut.nlSystemAPi.model.entity.CreditMemo.CreditMemoWithInvoiceReceipt;
import com.ut.nlSystemAPi.model.entity.SaleInvoice.*;
import com.ut.nlSystemAPi.model.entity.SaleOrder.*;
import com.ut.nlSystemAPi.model.filter.CreditMemoReceiptFilter;
import com.ut.nlSystemAPi.model.filter.SaleInvoiceFilter;
import com.ut.nlSystemAPi.model.filter.SalesInvoiceReceiptFilter;
import com.ut.nlSystemAPi.model.response.CreditMemo.CreditMemoResponse;
import com.ut.nlSystemAPi.model.response.Quotation.QuotationCreator;
import com.ut.nlSystemAPi.model.response.SaleInvoice.InvoiceDepositResponse;
import com.ut.nlSystemAPi.model.response.SaleInvoice.SaleInvoiceDetailResponse;
import com.ut.nlSystemAPi.model.response.SaleInvoice.SaleInvoiceResponse;
import com.ut.nlSystemAPi.model.response.SaleInvoice.SalesInvoiceReceiptResponse;
import com.ut.nlSystemAPi.model.response.TermCondition.TermConditionResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleInvoiceMapper {

  List<SaleInvoiceResponse> getList(@Param("filter") SaleInvoiceFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") SaleInvoiceFilter filter, @Param("userId") Long userId);

  List<SaleInvoiceResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

  Long getIdByCode(@Param("code") String code);

  Boolean insert(@Param("saleInvoice") SaleInvoice saleInvoice);

  Boolean update(@Param("saleInvoice") SaleInvoice saleInvoice);

  Boolean insertTermCondition(@Param("termCondition") SaleInvoiceTermCondition termCondition);

  List<TermConditionResponse> getTermCondition(@Param("saleInvoiceId") Long saleInvoiceId);

  List<InvoiceDepositResponse> getInvoiceDeposit(@Param("saleInvoiceId") Long saleInvoiceId);

  Boolean insertDetail(@Param("detail") SaleInvoiceDetail detail);

  Boolean insertService(@Param("service") SaleInvoiceServices service);

  Boolean insertMisc(@Param("misc") SaleInvoiceMisc misc);

  Boolean insertSaleOrderApplyDeposit(@Param("deposit") SaleInvoiceApplyDeposit deposit);

  Boolean insertStockOrder(@Param("stockOrder") StockOrder stockOrder);

  List<SaleInvoiceDetailResponse> getListDetail(@Param("saleInvoiceId") Long saleInvoiceId);

  Boolean approve(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

  QuotationCreator getCreator(@Param("id") Long id);

  Long countItems(@Param("saleInvoiceId") Long saleInvoiceId);

  Long checkDelivery(@Param("saleInvoiceId") Long saleInvoiceId);

  Double getTotalQtyStock(@Param("warehouseId") Long warehouseId, @Param("tableName") String tableName, @Param("productId") Long productId, @Param("locationStatus") Integer locationStatus);

  Double getTotalQtyOrder(@Param("salesInvoiceId") Long salesInvoiceId, @Param("warehouseId") Long warehouseId, @Param("productId") Long productId);

  Long getDepositChartAccountId(@Param("saleInvoiceId") Long saleInvoiceId);

  Double getDepositAmount(@Param("saleInvoiceId") Long saleInvoiceId);

  Boolean updateDetail(@Param("detail") SaleInvoiceDetail detail);

  Boolean updateService(@Param("service") SaleInvoiceServices service);

  Boolean updateMisc(@Param("misc") SaleInvoiceMisc misc);

  Double getTotalDepositAmount(@Param("saleInvoiceId") Long saleInvoiceId);

  Boolean closeDeposit(@Param("saleInvoiceId") Long saleInvoiceId);

  Boolean closeQuotation(@Param("quotationId") Long quotationId);

  Boolean closeSalesOrder(@Param("saleOrderId") Long saleOrderId, @Param("userId") Long userId);

  OrganizationLimitSetting getOrganizationLimitSetting(@Param("organizationId") Long organizationId);

  Boolean insertReceipt(@Param("receipt") SalesInvoiceReceipt receipt);

  Boolean updateBalance(@Param("salesInvoiceId") Long id, @Param("balance") Double balance, @Param("type") Long type);

  List<SalesInvoiceReceiptResponse> listReceipt(@Param("filter") SalesInvoiceReceiptFilter filter);

  Boolean voidReceipt(@Param("request") SalesInvoiceReceiptFilter request, @Param("userId") Long userId);

  Boolean closeRecurrence(@Param("code") String code, @Param("userId") Long userId);

  SalesInvoiceReceiptResponse getOneReceipt(@Param("id") Long id);

  Long getReceivePaymentId(@Param("salesInvoiceId") Long salesInvoiceId, @Param("paidUsd") Double paidUsd);
}
