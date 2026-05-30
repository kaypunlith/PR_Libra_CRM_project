package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.filter.Report.Sales.*;
import com.ut.nlSystemAPi.model.response.Report.ReportGrandTotalResponse;
import com.ut.nlSystemAPi.model.response.Report.Sales.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesReportMapper {

    // Customer Summary
    List<CustomerSummaryResponse> getListCustomerSummary(@Param("filter") CustomerSummaryFilter filter);

    List<CustomerSummaryTransactionResponse> getListCustomerSummaryTransaction(@Param("customerId") Long customerId, @Param("year") String year);

    Long countListCustomerSummary(@Param("filter") CustomerSummaryFilter filter);



    // Quotation
    List<QuotationReportResponse> getListQuotationDetail(@Param("filter") QuotationReportFilter filter);

    Long countListQuotationDetail(@Param("filter") QuotationReportFilter filter);

    List<QuotationReportResponse> getListQuotationByProduct(@Param("filter") QuotationReportFilter filter);

    Long countListQuotationByProduct(@Param("filter") QuotationReportFilter filter);

    List<QuotationReportDetailResponse> getListQuotationByProductDetail(@Param("quotationId") Long quotationId);

    List<QuotationReportResponse> getListQuotationByProductSummary(@Param("filter") QuotationReportFilter filter);

    Long countListQuotationByProductSummary(@Param("filter") QuotationReportFilter filter);



    // Sales Order
    List<SalesOrderReportResponse> getListSalesOrderDetail(@Param("filter") SalesOrderReportFilter filter);

    Long countListSalesOrderDetail(@Param("filter") SalesOrderReportFilter filter);

    List<SalesOrderReportResponse> getListSalesOrderByProduct(@Param("filter") SalesOrderReportFilter filter);

    Long countListSalesOrderByProduct(@Param("filter") SalesOrderReportFilter filter);

    List<SalesOrderReportDetailResponse> getListSalesOrderByProductDetail(@Param("salesOrderId") Long salesOrderId);

    List<SalesOrderReportResponse> getListSalesOrderByProductSummary(@Param("filter") SalesOrderReportFilter filter);

    Long countListSalesOrderByProductSummary(@Param("filter") SalesOrderReportFilter filter);




    // Sales Top Bottom Item
    List<SalesTopBottomItemReportResponse> getListSalesTopBottomItem(@Param("filter") SalesTopButtonReportFilter filter);

    Long countListSalesTopBottomItem(@Param("filter") SalesTopButtonReportFilter filter);





    // Sales Top Bottom Customer
    List<SalesTopBottomCustomerReportResponse> getListSalesTopBottomCustomer(@Param("filter") SalesTopButtonReportFilter filter);

    Long countListSalesTopBottomCustomer(@Param("filter") SalesTopButtonReportFilter filter);





    // Sales By Item
    List<SalesByItemReportResponse> getListSalesByItem(@Param("filter") SalesByItemReportFilter filter, @Param("userId") Long userId);

    Long countListSalesByItem(@Param("filter") SalesByItemReportFilter filter, @Param("userId") Long userId);

    ReportGrandTotalResponse sumGrandTotalSalesByItem(@Param("filter") SalesByItemReportFilter filter, @Param("userId") Long userId);






    // Sales By Item Type
    List<SalesByItemTypeReportResponse> getListSalesByItemType(@Param("filter") SalesByItemTypeReportFilter filter, @Param("userId") Long userId);

    Long countListSalesByItemType(@Param("filter") SalesByItemTypeReportFilter filter, @Param("userId") Long userId);

    ReportGrandTotalResponse sumGrandTotalSalesByItemType(@Param("filter") SalesByItemTypeReportFilter filter, @Param("userId") Long userId);





    // Sales By Customer
    List<SalesByCustomerReportResponse> getListSalesByCustomer(@Param("filter") SalesByCustomerReportFilter filter, @Param("userId") Long userId);

    Long countListSalesByCustomer(@Param("filter") SalesByCustomerReportFilter filter, @Param("userId") Long userId);

    ReportGrandTotalResponse sumGrandTotalSalesByCustomer(@Param("filter") SalesByCustomerReportFilter filter, @Param("userId") Long userId);




    // Sales By Rep
    List<SalesByCustomerReportResponse> getListSalesByRep(@Param("filter") SalesByCustomerReportFilter filter, @Param("userId") Long userId);

    Long countListSalesByRep(@Param("filter") SalesByCustomerReportFilter filter, @Param("userId") Long userId);

    ReportGrandTotalResponse sumGrandTotalSalesByRep(@Param("filter") SalesByCustomerReportFilter filter, @Param("userId") Long userId);




    // Total Sales
    List<TotalSalesReportResponse> getListTotalSales(@Param("filter") TotalSalesReportFilter filter);

    Long countListTotalSales(@Param("filter") TotalSalesReportFilter filter);




    // Sales Invoice
    List<SalesInvoiceReportResponse> getListSalesInvoiceDetail(@Param("filter") SalesInvoiceReportFilter filter, @Param("userId") Long userId);

    Long countListSalesInvoiceDetail(@Param("filter") SalesInvoiceReportFilter filter, @Param("userId") Long userId);

    List<SalesInvoiceReportResponse> getListSalesInvoiceItem(@Param("filter") SalesInvoiceReportFilter filter, @Param("userId") Long userId);

    List<SalesInvoiceReportResponse> getListSalesInvoiceItemDetail(@Param("organizationId") Long organizationId, @Param("userId") Long userId);

    Long countListSalesInvoiceItem(@Param("filter") SalesInvoiceReportFilter filter, @Param("userId") Long userId);




    // Invoice By Rep
    List<InvoiceByRepReportResponse> getListInvoiceByRep(@Param("filter") InvoiceByRepReportFilter filter, @Param("userId") Long userId);

    Long countListInvoiceByRep(@Param("filter") InvoiceByRepReportFilter filter, @Param("userId") Long userId);




    // Open Invoice By Rep
    List<InvoiceByRepReportResponse> getListOpenInvoiceByRep(@Param("filter") OpenInvoiceByRepReportFilter filter, @Param("userId") Long userId);

    Long countListOpenInvoiceByRep(@Param("filter") OpenInvoiceByRepReportFilter filter, @Param("userId") Long userId);



    // Invoice Credit Memo
    List<InvoiceCreditMemoReportResponse> getListInvoiceCreditMemo(@Param("filter") InvoiceCreditMemoReportFilter filter, @Param("userId") Long userId);

    Long countListInvoiceCreditMemo(@Param("filter") InvoiceCreditMemoReportFilter filter, @Param("userId") Long userId);






    // Discount Summary
    List<DiscountSummaryReportResponse> getListDiscountSummary(@Param("filter") DiscountSummaryReportFilter filter, @Param("userId") Long userId);

    Long countListDiscountSummary(@Param("filter") DiscountSummaryReportFilter filter, @Param("userId") Long userId);
    
    
    
    

    // Delivery Note
    List<DeliveryNoteReportResponse> getListDeliveryNote(@Param("filter") DeliveryNoteReportFilter filter);

    Long countListDeliveryNote(@Param("filter") DeliveryNoteReportFilter filter);




    // E-Commerce User
    List<ECommerceUserReportResponse> getListECommerceUser(@Param("filter") ECommerceUserReportFilter filter);

    Long countListECommerceUser(@Param("filter") ECommerceUserReportFilter filter);




    // Receive Payment
    List<ReceivePaymentReportResponse> getListReceivePayment(@Param("filter") ReceivePaymentReportFilter filter, @Param("userId") Long userId);

    Long countListReceivePayment(@Param("filter") ReceivePaymentReportFilter filter, @Param("userId") Long userId);





    // Receive Payment By Rep
    List<ReceivePaymentByRepReportResponse> getListReceivePaymentByRep(@Param("filter") ReceivePaymentByRepReportFilter filter, @Param("userId") Long userId);

    Long countListReceivePaymentByRep(@Param("filter") ReceivePaymentByRepReportFilter filter, @Param("userId") Long userId);


}
