package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.filter.Report.Organization.*;
import com.ut.nlSystemAPi.model.response.Report.Organization.*;
import com.ut.nlSystemAPi.model.response.Report.ReportGrandTotalResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationReportMapper {

    List<AccountReceivableAgingReportResponse> getListAccountReceivableAging(@Param("filter") AccountReceivableAgingReportFilter filter, @Param("userId") Long userId);

    Long countListAccountReceivableAging(@Param("filter") AccountReceivableAgingReportFilter filter, @Param("userId") Long userId);

    Double getCurrentAmount(
            @Param("filter") AccountReceivableAgingReportFilter filter,
            @Param("userId") Long userId,
            @Param("customerId") Long customerId);

    Double getAgingAmount(
            @Param("filter") AccountReceivableAgingReportFilter filter,
            @Param("userId") Long userId,
            @Param("customerId") Long customerId,
            @Param("fromDays") Long fromDays,
            @Param("toDays") Long toDays);

    Double getOverAgingAmount(
            @Param("filter") AccountReceivableAgingReportFilter filter,
            @Param("userId") Long userId,
            @Param("customerId") Long customerId,
            @Param("throughDays") Long throughDays);




    // Customer Balance
    List<CustomerBalanceReportResponse> getListCustomerBalance(@Param("filter") CustomerBalanceReportFilter filter, @Param("userId") Long userId);

    Long countListCustomerBalance(@Param("filter") CustomerBalanceReportFilter filter, @Param("userId") Long userId);

    ReportGrandTotalResponse sumGrandTotalCustomerBalance(@Param("filter") CustomerBalanceReportFilter filter, @Param("userId") Long userId);




    // Customer Balance By Invoice
    List<CustomerBalanceByInvoiceReportResponse> getListCustomerBalanceByInvoice(@Param("filter") CustomerBalanceByInvoiceReportFilter filter, @Param("userId") Long userId);

    Long countListCustomerBalanceByInvoice(@Param("filter") CustomerBalanceByInvoiceReportFilter filter, @Param("userId") Long userId);

    ReportGrandTotalResponse sumGrandTotalCustomerBalanceByInvoice(@Param("filter") CustomerBalanceByInvoiceReportFilter filter, @Param("userId") Long userId);




    // Statement
    List<StatementReportResponse> getListStatement(@Param("filter") StatementReportFilter filter);

    Long countListStatement(@Param("filter") StatementReportFilter filter);

    Double getCurrentAmountStatement(
            @Param("filter") StatementReportFilter filter,
            @Param("userId") Long userId,
            @Param("customerId") Long customerId);

    Double getAgingAmountStatement(
            @Param("filter") StatementReportFilter filter,
            @Param("userId") Long userId,
            @Param("customerId") Long customerId,
            @Param("fromDays") Long fromDays,
            @Param("toDays") Long toDays);

    Double getOverAgingAmountStatement(
            @Param("filter") StatementReportFilter filter,
            @Param("userId") Long userId,
            @Param("customerId") Long customerId,
            @Param("throughDays") Long throughDays);





    // Customer Address
    List<CustomerAddressReportResponse> getListCustomerAddress(@Param("filter") CustomerAddressReportFilter filter);

    Long countListCustomerAddress(@Param("filter") CustomerAddressReportFilter filter);





    // Customer Address List
    List<CustomerAddressReportResponse> getListCustomerAddressList(@Param("filter") CustomerAddressListReportFilter filter);

    Long countListCustomerAddressList(@Param("filter") CustomerAddressListReportFilter filter);





    // Customer Address Detail
    List<CustomerAddressReportResponse> getListCustomerAddressDetail(@Param("filter") CustomerAddressListReportFilter filter);

    Long countListCustomerAddressDetail(@Param("filter") CustomerAddressListReportFilter filter);





    // SO Balance
    List<SOBalanceReportResponse> getListSOBalance(@Param("filter") SOBalanceReportFilter filter);

    Long countListSOBalance(@Param("filter") SOBalanceReportFilter filter);





    // Activity Card Tracking
    List<ActivityCardTrackingReportResponse> getListActivityCardTracking(@Param("filter") ActivityCardTrackingReportFilter filter);

    Long countListActivityCardTracking(@Param("filter") ActivityCardTrackingReportFilter filter);

}
