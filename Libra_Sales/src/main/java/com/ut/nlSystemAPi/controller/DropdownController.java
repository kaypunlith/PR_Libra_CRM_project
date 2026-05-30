package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.Dropdown.*;
import com.ut.nlSystemAPi.model.filter.TermConditionFilter;
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

    @PostMapping("/list-module-type")
    @ApiOperation(value = "List module type dropdown by filter", notes = "termConditionModules == 1 For List On Term & Condition Apply", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listModuleType(@RequestBody DropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListModuleType(filter, httpServletRequest);
    }

    @PostMapping("/list-term-condition")
    @ApiOperation(value = "List term condition by filter", notes = "termConditionTypeId: follow dropdown: /list-term-condition-type", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listTermCondition(@RequestBody TermConditionFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListTermCondition(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-activity-status")
    @ApiOperation(value = "List organization activity status by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listActivityStatus(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListActivityStatus(filter, httpServletRequest);
    }

    @PostMapping("/list-term-condition-type")
    @ApiOperation(value = "List term condition type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listTermConditionType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListTermConditionType(filter, httpServletRequest);
    }

    @PostMapping("/list-organization")
    @ApiOperation(value = "List organization by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganization(@RequestBody OrganizationDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganization(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-contact")
    @ApiOperation(value = "List organization contact by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationContact(@RequestBody CustomerContactDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganizationContact(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-group")
    @ApiOperation(value = "List organization group by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationGroup(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganizationGroup(filter, httpServletRequest);
    }

    @PostMapping("/list-zone")
    @ApiOperation(value = "List Zone by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listZone(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListZone(filter, httpServletRequest);
    }

    @PostMapping("/list-market")
    @ApiOperation(value = "List Market by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listMarket(@RequestBody MarketDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListMarket(filter, httpServletRequest);
    }

    @PostMapping("/list-boq")
    @ApiOperation(value = "List BOQ by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listBOQ(@RequestBody BOQDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListBOQ(filter, httpServletRequest);
    }

    @PostMapping("/list-bom")
    @ApiOperation(value = "List BOM by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listBOM(@RequestBody BOMDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListBOM(filter, httpServletRequest);
    }

    @PostMapping("/list-project-estimation-term")
    @ApiOperation(value = "List project estimation term by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListProjectEstimationTerm(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListProjectEstimationTerm(filter, httpServletRequest);
    }

    @PostMapping("/list-quotation")
    @ApiOperation(value = "List quotation by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListQuotation(@RequestBody QuotationDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListQuotation(filter, httpServletRequest);
    }

    @PostMapping("/list-transfer-order")
    @ApiOperation(value = "List Transfer Order by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listTransferOrder(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListTransferOrder(filter, httpServletRequest);
    }

    @PostMapping("/list-sales-invoice")
    @ApiOperation(value = "List Sales Invoice by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listSalesInvoice(@RequestBody SalesInvoiceDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListSalesInvoice(filter, httpServletRequest);
    }

    @PostMapping("/list-cm-term")
    @ApiOperation(value = "List CM Term by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCMTerm(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCMTerm(filter, httpServletRequest);
    }

    @PostMapping("/list-business-activity")
    @ApiOperation(value = "List Business Activity by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listBusinessActivity(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListBusinessActivity(filter, httpServletRequest);
    }

    @PostMapping("/list-business-type")
    @ApiOperation(value = "List Business Type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listBusinessType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListBusinessType(filter, httpServletRequest);
    }

    @PostMapping("/list-key-negotiation-issue")
    @ApiOperation(value = "List Key Negotiation Issue by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listKeyNegotiationIssue(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListKeyNegotiationIssue(filter, httpServletRequest);
    }

    @PostMapping("/list-country")
    @ApiOperation(value = "List Country by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCountry(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCountry(filter, httpServletRequest);
    }

    @PostMapping("/list-street")
    @ApiOperation(value = "List Street by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listStreet(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListStreet(filter, httpServletRequest);
    }

    @PostMapping("/list-province")
    @ApiOperation(value = "List Province by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProvince(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListProvince(filter, httpServletRequest);
    }

    @PostMapping("/list-district")
    @ApiOperation(value = "List District by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listDistrict(@RequestBody DistrictDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListDistrict(filter, httpServletRequest);
    }

    @PostMapping("/list-commune")
    @ApiOperation(value = "List Commune by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCommune(@RequestBody CommuneDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCommune(filter, httpServletRequest);
    }

    @PostMapping("/list-village")
    @ApiOperation(value = "List Village by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listVillage(@RequestBody VillageDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListVillage(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-contact-family")
    @ApiOperation(value = "List Organization Contact Family by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationContactFamily(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganizationContactFamily(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-contact-location")
    @ApiOperation(value = "List Organization Contact Location by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationContactLocation(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganizationContactLocation(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-contact-character")
    @ApiOperation(value = "List Organization Contact Progress by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationContactCharacter(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganizationContactCharacter(filter, httpServletRequest);
    }


    @PostMapping("/list-organization-contact-progress")
    @ApiOperation(value = "List Organization Contact Progress by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationContactProgress(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganizationContactProgress(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-contact-brand")
    @ApiOperation(value = "List Organization Contact Brand by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationContactBrand(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganizationContactBrand(filter, httpServletRequest);
    }

    @PostMapping("/list-organization-contact-description-type")
    @ApiOperation(value = "List Organization Contact Description Type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listOrganizationContactDescriptionType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListOrganizationContactDescriptionType(filter, httpServletRequest);
    }

    @PostMapping("/list-sales-target-period")
    @ApiOperation(value = "List Sales Target Period by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listSalesTargetPeriod(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListSalesTargetPeriod(filter, httpServletRequest);
    }

    @PostMapping("/list-competitor-business-type")
    @ApiOperation(value = "List Competitor Business Type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompetitorBusinessType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompetitorBusinessType(filter, httpServletRequest);
    }

    @PostMapping("/list-competitor-priority")
    @ApiOperation(value = "List Competitor Priority by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompetitorPriority(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompetitorPriority(filter, httpServletRequest);
    }

    @PostMapping("/list-competitor-employee-amount")
    @ApiOperation(value = "List Competitor Employee Amount by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompetitorEmployeeAmount(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompetitorEmployeeAmount(filter, httpServletRequest);
    }

    @PostMapping("/list-competitor-main-product")
    @ApiOperation(value = "List Competitor Main Product by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompetitorMainProduct(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompetitorMainProduct(filter, httpServletRequest);
    }

    @PostMapping("/list-competitor-strength")
    @ApiOperation(value = "List Competitor Strength by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompetitorStrength(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompetitorStrength(filter, httpServletRequest);
    }

    @PostMapping("/list-competitor-weakness")
    @ApiOperation(value = "List Competitor Weakness by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompetitorWeakness(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompetitorWeakness(filter, httpServletRequest);
    }

    @PostMapping("/list-competitor-stage")
    @ApiOperation(value = "List Competitor Stage by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompetitorStage(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompetitorStage(filter, httpServletRequest);
    }

    @PostMapping("/list-competitor-status")
    @ApiOperation(value = "List Competitor Status by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompetitorStatus(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompetitorStatus(filter, httpServletRequest);
    }

    @PostMapping("/list-quotation-division")
    @ApiOperation(value = "List years by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listQuotationDivision(@RequestBody QuotationDivisionDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListQuotationDivision(filter, httpServletRequest);
    }

    @PostMapping("/list-quotation-status")
    @ApiOperation(value = "List years by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listQuotationStatus(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListQuotationStatus(filter, httpServletRequest);
    }

    @PostMapping("/list-quotation-status-reason")
    @ApiOperation(value = "List years by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listQuotationStatusReason(@RequestBody QuotationStatusReasonDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListQuotationStatusReason(filter, httpServletRequest);
    }

    @PostMapping("/list-freedom-customer")
    @ApiOperation(value = "List Freedom customer by filter")
    public ResponseMessage<BaseResult> listFreedomCustomer(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return dropdownService.getListFreedomCustomer(filter, httpServletRequest);
    }

    @PostMapping("/list-freedom-warehouse")
    @ApiOperation(value = "List Freedom warehouse by filter")
    public ResponseMessage<BaseResult> listFreedomWarehouse(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return dropdownService.getListFreedomWarehouse(filter, httpServletRequest);
    }

    @PostMapping("/list-years")
    @ApiOperation(value = "List years by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listYears(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListYears(filter, httpServletRequest);
    }
}
