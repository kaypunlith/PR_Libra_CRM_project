package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.model.response.Dropdown.*;
import com.ut.nlSystemAPi.model.response.LandedCostType.LandedCostTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DropdownMapper {

    List<GroupApiDropdownResponse> getListGroupApi(@Param("filter") Filter filter);

    List<UserDropdownResponse> getListUser(@Param("filter") Filter filter);

    List<CompanyDropdownResponse> getListCompany(@Param("filter") Filter filter, @Param("userId") Long userId);

    List<ModuleTypeDropdownResponse> getListModuleType(@Param("filter") Filter filter);

    List<EmployeeDropdownResponse> getListEmployee(@Param("filter") EmployeeDropdownFilter filter, @Param("userId") Long userId);

    List<EmployeeGroupDropdownResponse> getListGroup(@Param("filter") GroupDropdownFilter filter);

    List<StatusDropdownResponse> getListStatus(@Param("filter") Filter filter);

    List<PriorityDropdownResponse> getListPriority(@Param("filter") Filter filter);

    List<PaymentTypeDropdownResponse> getListPaymentType(@Param("filter") Filter filter);

    List<WarehouseDropDownResponse> getListWarehouse(@Param("filter") WarehouseFilter filter, @Param("userId") Long userId);

    List<ClassDropdownResponse> getListClass(@Param("filter") Filter filter);

    List<SubGroupDropdownResponse> getListSubGroup(@Param("filter") Filter filter);

    List<PriceTypeDropDownResponse> getListPriceType(@Param("filter") PriceTypeDropdownFilter filter);

    List<OrganizationDropdownResponse> getOrganization(@Param("filter") Filter filter);

    List<OrganizationDropdownResponse> getOrganizationContact(@Param("filter") OrganizationContactFilter filter);

    List<ProductGroupDownResponse>getProductGroup(@Param("filter") Filter filter);

    List<ProductDropdownResponse>getProduct(@Param("filter") ProductFilter filter);

    List<SectionDropdownResponse>getSection(@Param("filter") Filter filter);

    List<LocationDropdownResponse>getLocation(@Param("filter") LocationFilter filter);

    List<ServiceDropdownResponse>getService(@Param("filter") ServiceFilter filter);

    List<ChartAccountDropdownResponse>getChartAccount(@Param("filter") ChartAccountDropdownFilter filter, @Param("userId") Long userId);

    List<PriceRequestPriorityDropdownResponse> getListPriceRequestPriority(@Param("filter") Filter filter);

    List<PriceRequestPercentageDropdownResponse> getListPriceRequestPercentage(@Param("filter") Filter filter);

    List<MadeInCountryDropdownResponse> getListMadeInCountry(@Param("filter") Filter filter);

    List<CurrencyCenterDropdownResponse> getListCurrencyCenter(@Param("filter") VendorContactFilter filter);

    List<VendorDropdownResponse> getListVendor(@Param("filter") VendorFilter filter);

    List<PriceRequestVendorDropdownResponse> getListPriceRequestVendor(@Param("filter") Filter filter);

    List<UomsDropdownResponse> getListUoms(@Param("filter") UomsFilter filter);

    List<UomsDropdownResponse> getListUomsProduct(@Param("filter") UomsProductFilter filter);

    List<DeliveryStatusDropdownResponse> getListDeliveryStatus(@Param("filter") Filter filter);

    List<LandedCostTypeResponse> getListLandedCostType(@Param("filter") Filter filter);

    List<VatSettingDropdownResponse> getListVatSetting(@Param("filter") VatSettingDropdownFilter filter);

    List<PaymentTermDropdownResponse> getListPaymentTerm(@Param("filter") Filter filter);

    List<ProductGroupDropdownResponse> getListProductGroup(@Param("filter") Filter filter);

    List<OrderResponse> getListOrder(@Param("filter") SaleOrderDropdownFilter filter);

    List<OrderDetailResponse> getListOrderDetail(@Param("orderId") Long id);

//    List<UomOrderDetailResponse> getListUomOrderDetail(@Param("filter") Filter filter);

    List<PurchaseOrderDropdownResponse> getListPurchaseOrder(@Param("filter") PurchaseOrderDropdownFilter filter);

    List<PurchaseOrderDetailDropdownResponse> getListPurchaseOrderDetail(@Param("purchaseOrderId") Long id);

    Long getPaymentTypeById(@Param("id") Long id);

    List<VendorDropdownResponse> getErByPo(@Param("id") Long id);

    List<ExchangeRateDropDownResponse> getErExchangeRateByPo(@Param("id") Long id);

    Long getQtyRemain(@Param("productId") Long productId,@Param("purchaseOrderId") Long purchaseOrderId);

    Long countListPurchaseBill(@Param("filter") VendorContactFilter filter);

    List<PurchaseBillDropdownResponse> getListPurchaseBill(@Param("filter") PurchaseBillDropdownFilter filter);

    List<PurchaseBillDetailDropdownResponse> getListPurchaseBillDetail(@Param("purchaseOrderId") Long id);

    List<DepartmentDropDownResponse> getListDepartment(@Param("filter") Filter filter);

    List<SkuDropdownResponse> getListSku(@Param("filter") Filter filter);

    List<VendorDropdownResponse> getListVendorContact(@Param("filter") VendorContactFilter filter);

    List<VendorDropdownResponse> getListVendorGroup(@Param("filter") Filter filter);

    List<VendorDropdownResponse> getListOrganizationGroup(@Param("filter") Filter filter);

    List<VendorDropdownResponse> getListProductCategory(@Param("filter") Filter filter);

    List<CompanyCodeDropdownResponse> getListCompanyCode(@Param("filter") CompanyCodeFilter filter);

    List<VendorDropdownResponse> getListERNumber(@Param("filter") Filter filter);

    Long countListERNumber(@Param("filter") Filter filter);

    List<RequestStockDropdownResponse> getListRequestStock(@Param("filter") RequestStockDropdownFilter filter);

    List<ProductSkuDropdownResponse> getListProductSku(@Param("filter") ProductSkuFilter filter);

    List<PrCloseDropdownResponse> getListPrClose(@Param("filter") Filter filter);

    List<DiscountDropdownResponse> getListDiscount(@Param("filter") Filter filter);

    List<ExchangeRateDropDownResponse> getListExchangeRate(@Param("filter") Filter filter);

    List<ShipmentDropdownResponse> getListShipment(@Param("filter") Filter filter);

    List<ProductQtyDetailResponse> getProductQtyDetails(@Param("filter") ProductQtyDetailFilter filter, @Param("userId") Long userId);
}
