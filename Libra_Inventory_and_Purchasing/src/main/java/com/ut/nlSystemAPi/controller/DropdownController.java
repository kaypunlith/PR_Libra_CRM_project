package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.service.DropdownService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/dropdown")
@Api(tags = "10. Dropdown", description = "Dropdown Resource")
public class DropdownController {

    @Autowired
    private DropdownService dropdownService;

    @PostMapping("/list-group-api")
    @ApiOperation(value = "List group api by filter", notes = "List group api by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listGroupApi(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListGroupApi(filter, httpServletRequest);
    }

    @PostMapping("/list-user")
    @ApiOperation(value = "List user by filter", notes = "List user by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listUser(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListUser(filter, httpServletRequest);
    }

    @PostMapping("/list-company")
    @ApiOperation(value = "List company by filter", notes = "List company by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompany(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompany(filter, httpServletRequest);
    }

    @PostMapping("/list-module-type")
    @ApiOperation(value = "List module type dropdown by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listModuleType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListModuleType(filter, httpServletRequest);
    }

    @PostMapping("/list-employee")
    @ApiOperation(value = "List employee by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listEmployee(@RequestBody EmployeeDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListEmployee(filter, httpServletRequest);
    }

    @PostMapping("/list-employee-group")
    @ApiOperation(value = "List group by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listGroup(@RequestBody GroupDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListGroup(filter, httpServletRequest);
    }

    @PostMapping("/list-status")
    @ApiOperation(value = "List status by filter", notes = "List status by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listStatus(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListStatus(filter, httpServletRequest);
    }

    @PostMapping("/list-priority")
    @ApiOperation(value = "List priority by filter", notes = "List priority by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPriority(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPriority(filter, httpServletRequest);
    }


    @PostMapping("/list-payment-type")
    @ApiOperation(value = "List payment type by filter", notes = "List priority by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPaymentType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
//         Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPaymentType(filter, httpServletRequest);
    }

    @PostMapping("/list-warehouse")
    @ApiOperation(value = "List warehouse by filter", notes = "List warehouse by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listWarehouse(@RequestBody WarehouseFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
//         Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListWarehouse(filter, httpServletRequest);
    }

    @PostMapping("/list-class")
    @ApiOperation(value = "List class by filter", notes = "List priority by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listClassList(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
//         Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListClass(filter, httpServletRequest);
    }

    @PostMapping("/list-sub-group")
    @ApiOperation(value = "List sub group by filter", notes = "List priority by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listlistSubGroup(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
//         Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListSubGroup(filter, httpServletRequest);
    }

    @PostMapping("/list-price-type")
    @ApiOperation(value = "List price type by filter", notes = "List priority by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPriceType(@RequestBody PriceTypeDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
//         Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getPriceType(filter, httpServletRequest);
    }

    @PostMapping("/list-organization")
    @ApiOperation(value = "List organization by filter", notes = "List priority by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganization(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
//         Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getOrganization(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-contact")
    @ApiOperation(value = "List organization contact by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationContact(@RequestBody OrganizationContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getOrganizationContact(filter, httpServletRequest);
    }

    @PostMapping("/list-price_request_priority")
    @ApiOperation(value = "List price request priority by filter", notes = "List price request priority by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPriceRequestPriority(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPriceRequestPriority(filter, httpServletRequest);
    }

    @PostMapping("/list-price_request_percentage")
    @ApiOperation(value = "List price request percentage by filter", notes = "List price request percentage by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPriceRequestPercentage(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPriceRequestPercentage(filter, httpServletRequest);
    }

    @PostMapping("/list-made_in_country")
    @ApiOperation(value = "List made in country by filter", notes = "List made in country by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listMadeInCountry(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListMadeInCountry(filter, httpServletRequest);
    }
    @PostMapping("/list-currency-center")
    @ApiOperation(value = "List currency center by filter", notes = "List currency center by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCurrencyCenter(@RequestBody VendorContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCurrencyCenter(filter, httpServletRequest);
    }

    @PostMapping("/list-vendor")
    @ApiOperation(value = "List vendor by filter", notes = "List vendor by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listVendor(@RequestBody VendorFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListVendor(filter, httpServletRequest);
    }

    @PostMapping("/list-price_request_vendor")
    @ApiOperation(value = "List price request vendor by filter", notes = "List price request vendor by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPriceRequestVendor(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPriceRequestVendor(filter, httpServletRequest);
    }

    @PostMapping("/list-uoms")
    @ApiOperation(value = "List uoms by filter", notes = "UomsType: 1 = Main Uom, 2 = Small Uom", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listUoms(@RequestBody UomsFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListUoms(filter, httpServletRequest);
    }

    @PostMapping("/list-uoms-conversion")
    @ApiOperation(value = "List uoms conversion of product by product id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listUomsProduct(@RequestBody UomsProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListUomsProduct(filter, httpServletRequest);
    }

    @PostMapping("/list-delivery-status")
    @ApiOperation(value = "List delivery status by filter", notes = "List delivery status by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listDeliveryStatus(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListDeliveryStatus(filter, httpServletRequest);
    }

    @PostMapping("/list-product_group")
    @ApiOperation(value = "List product group by filter", notes = "List product group by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProductGroup(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListProductGroup(filter, httpServletRequest);
    }

    @PostMapping("/list/order")
    @ApiOperation(value = "List order by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrder(@RequestBody SaleOrderDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.listOrder(filter, httpServletRequest);
    }

    @PostMapping("/list/order-detail/{id}")
    @ApiOperation(value = "List order detail by order id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrderDetail(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.listOrderDetail(id, httpServletRequest);
    }

    @PostMapping("/list-product")
    @ApiOperation(value = "List product by filter", notes = "List product group by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProducts(@RequestBody ProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListProduct(filter, httpServletRequest);
    }

    @PostMapping("/list-section")
    @ApiOperation(value = "List section by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listSection(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListSection(filter, httpServletRequest);
    }

    @PostMapping("/list-service")
    @ApiOperation(value = "List service by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listService(@RequestBody ServiceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListService(filter, httpServletRequest);
    }

    @PostMapping("/list-location")
    @ApiOperation(value = "List location by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listLocation(@RequestBody LocationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListLocation(filter, httpServletRequest);
    }

    @PostMapping("/list-purchase-order")
    @ApiOperation(value = "List purchase order by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPurchaseOrder(@RequestBody PurchaseOrderDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPurchaseOrder(filter, httpServletRequest);
    }

    @PostMapping("/list/purchase-order-detail/{id}")
    @ApiOperation(value = "List purchase order detail by order id", notes = "Delete role", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPurchaseOrderDetail(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPurchaseOrderDetail(id, httpServletRequest);
    }

    @PostMapping("/list-purchase-bill")
    @ApiOperation(value = "List purchase bill by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPurchaseBill(@RequestBody PurchaseBillDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPurchaseBill(filter, httpServletRequest);
    }

    @PostMapping("/list/purchase-bill-detail/{id}")
    @ApiOperation(value = "List purchase bill detail by id", notes = "Delete role", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPurchaseBillDetail(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPurchaseBillDetail(id, httpServletRequest);
    }

    @PostMapping("/list-chart-account")
    @ApiOperation(value = "List chart account by filter", notes = "If Parent Id = 0 means No Parent", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listChartAccount(@RequestBody ChartAccountDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListChartAccount(filter, httpServletRequest);
    }

    @PostMapping("/list-landed-cost-type")
    @ApiOperation(value = "List landed cost type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listLandedCostType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListLandedCostType(filter, httpServletRequest);
    }

    @PostMapping("/list-vat-setting")
    @ApiOperation(value = "List vat setting by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listVatSetting(@RequestBody VatSettingDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListVatSetting(filter, httpServletRequest);
    }

    @PostMapping("/list-payment-terms")
    @ApiOperation(value = "List payment terms by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPaymentTerms(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPaymentTerms(filter, httpServletRequest);
    }

    @PostMapping("/list-department")
    @ApiOperation(value = "List payment terms by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listDepartment(@RequestBody DepartmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListDepartment(filter, httpServletRequest);
    }

    @PostMapping("/list-sku")
    @ApiOperation(value = "List payment terms by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listSku(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListSku(filter, httpServletRequest);
    }

    @PostMapping("/list-vendor-contact")
    @ApiOperation(value = "List vendor contact by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listVendorContact(@RequestBody VendorContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListVendorContact(filter, httpServletRequest);
    }

    @PostMapping("/list-vendor-group")
    @ApiOperation(value = "List vendor contact by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listVendorGroup(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListVendorGroup(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-group")
    @ApiOperation(value = "List organization group by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationGroup(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganizationGroup(filter, httpServletRequest);
    }

    @PostMapping("/list-product-category")
    @ApiOperation(value = "List product category by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProductCategory(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListProductCategory(filter, httpServletRequest);
    }

    @PostMapping("/list-company-code")
    @ApiOperation(value = "List company code by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompanyCode(@RequestBody CompanyCodeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompanyCode(filter, httpServletRequest);
    }

    @PostMapping("/list-er-number")
    @ApiOperation(value = "List ER number by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListERNumber(filter, httpServletRequest);
    }

    @PostMapping("/list-request-stock")
    @ApiOperation(value = "List purchase order by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listRequestStock(@RequestBody RequestStockDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListRequestStock(filter, httpServletRequest);
    }

    @PostMapping("/list-product-sku")
    @ApiOperation(value = "List product by sku", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProductSku(@RequestBody ProductSkuFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListProductSku(filter, httpServletRequest);
    }

    @PostMapping("/list-pr-close-reason")
    @ApiOperation(value = "List product by sku", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListPrClose(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListPrClose(filter, httpServletRequest);
    }

    @PostMapping("/list-discount")
    @ApiOperation(value = "List product by sku", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListDiscount(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListDiscount(filter, httpServletRequest);
    }


    @PostMapping("/list-exchange-rate")
    @ApiOperation(value = "List exchange rate by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListExchangeRate(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListExchangeRate(filter, httpServletRequest);
    }

    @PostMapping("/list-shipment")
    @ApiOperation(value = "List shipment by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListShipment(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListShipment(filter, httpServletRequest);
    }

    @PostMapping("/product-qty-detail")
    @ApiOperation(value = "Get product qty details by location", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getProductQtyDetail(@RequestBody ProductQtyDetailFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getProductQtyDetail(filter, httpServletRequest);
    }

}
