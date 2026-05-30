package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.AccountTypeFilter;
import com.ut.nlSystemAPi.model.filter.FindReferenceCodeFilter;
import com.ut.nlSystemAPi.model.filter.PurchaseOrderFilter;
import com.ut.nlSystemAPi.model.filter.ReconcileEndingDateReportFilter;
import com.ut.nlSystemAPi.model.response.Dropdown.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DropdownMapper {

    List<NationalityDropdownResponse> getListNationality(@Param("filter") Filter filter);

    List<GroupApiDropdownResponse> getListGroupApi(@Param("filter") Filter filter);

    List<UserDropdownResponse> getListUser(@Param("filter") Filter filter);

    List<CompanyDropdownResponse> getListCompany(@Param("filter") Filter filter);

    List<ChartAccountTypeDropdownResponse> getListChartAccountType(@Param("filter") Filter filter);

    List<ChartAccountGroupDropdownResponse> getListChartAccountGroup(@Param("filter") AccountTypeFilter filter);

    List<ClassDropdownResponse> getListClass(@Param("filter") Filter filter);

    List<ProductDropdownResponse> getListProduct(@Param("filter") Filter filter);

    List<AccountTypeDropdownResponse> getListAccountType(@Param("filter") Filter filter);

    List<ChartAccountDropdownResponse> getListChartAccount(@Param("filter") Filter filter);

    List<ChartAccountDropdownResponse> getListChartAccountBalance(@Param("filter") Filter filter);

    List<ProvinceDropdownResponse> getListProvince(@Param("filter") Filter filter);

    List<ModuleTypeDropdownResponse> getListModuleType(@Param("filter") Filter filter);

    List<CustomerDropdownResponse> getListCustomer(@Param("filter") Filter filter);

    List<EmployeeDropdownResponse> getListEmployee(@Param("filter") Filter filter);

    List<VendorDropdownResponse> getListVendor(@Param("filter") Filter filter);

    List<ARScheduleInvoiceDropdownResponse> getListARInvoice(@Param("filter") Filter filter);

    List<APScheduleInvoiceDropdownResponse> getListAPInvoice(@Param("filter") Filter filter);

    List<LocationDropdownResponse> getListLocation(@Param("filter") Filter filter);

    List<AssetDropdownResponse> getListAsset(@Param("filter") Filter filter);

    List<ZoneDropdownResponse> getListCustomerGroup(@Param("filter") Filter filter);

    List<ZoneDropdownResponse> getListCustomeContact(@Param("filter") Filter filter);

    List<DateFormatResponse> getListDateFormat(@Param("filter") Filter filter);

    List<DateFormatResponse> getListTimeFormat(@Param("filter") Filter filter);

    List<ReconcileDropDownResponse> getReconcile(@Param("filter") Filter filter);


    List<BranchDropDownResponse> getBranch(@Param("filter") Filter filter);


    List<BranchTypeDropDownResponse> getBranchType(@Param("filter") Filter filter);


    List<WarehouseDropDownResponse> getWarehouse(@Param("filter") Filter filter);

    List<WarehouseDropDownResponse> getCountries(@Param("filter") Filter filter);

    List<ExchangeRateDropDownResponse> getExchangeRate(@Param("filter") Filter filter);

    List<PurchaseOrderDropdownResponse> getPurchaseOrderList(@Param("filter") PurchaseOrderFilter filter);

    Long getCountPurchaseOrderList(@Param("filter") PurchaseOrderFilter filter);

    List<PurchaseOrderDropdownResponse> getPurchaseBillList(@Param("filter") Filter filter);

    Long getCountPurchaseBill(@Param("filter") PurchaseOrderFilter filter);

    List<PurchaseOrderDropdownResponse> getQuotationList(@Param("filter") Filter filter);

    Long getCountQuotation(@Param("filter") PurchaseOrderFilter filter);

    List<PurchaseOrderDropdownResponse> getSalesOrderList(@Param("filter") Filter filter);

    Long getCountSalesOrder(@Param("filter") PurchaseOrderFilter filter);

    List<PurchaseOrderDropdownResponse> getStatementEndingDate(@Param("filter") ReconcileEndingDateReportFilter filter);

    Long getCountStatementEndingDate(@Param("filter") ReconcileEndingDateReportFilter filter);

    String getBranchCodeByModule(@Param("filter") FindReferenceCodeFilter filter);

    List<CustomerDropdownResponse> getListReceivedPaymentCustomer(@Param("filter") Filter filter);

    // Purchase Bill detail (items + services) by purchase_order_id
    List<PurchaseOrderDetailDropdownResponse> getPurchaseBillDetail(@Param("id") Long id);

    // Purchase Order detail (from purchase requests: items + services)
    List<PurchaseOrderDetailDropdownResponse> getPurchaseOrderDetail(@Param("id") Long id);

    // Quotation detail (items + services + miscs + breaks)
    List<PurchaseOrderDetailDropdownResponse> getQuotationDetail(@Param("id") Long id);

    // Sales Order detail (items + services + miscs + breaks)
    List<PurchaseOrderDetailDropdownResponse> getSalesOrderDetail(@Param("id") Long id);

}
