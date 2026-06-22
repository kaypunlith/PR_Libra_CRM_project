package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.DepartmentFilter;
import com.ut.nlSystemAPi.model.filter.Dropdown.*;
import com.ut.nlSystemAPi.model.filter.TermConditionFilter;
import com.ut.nlSystemAPi.model.response.Dropdown.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DropdownMapper {

    List<DropdownResponse> getListModuleType(@Param("filter") DropdownFilter filter);

    List<DropdownResponse> getListDepartment(@Param("filter") DepartmentFilter filter);

    List<DropdownResponse> getListTermCondition(@Param("filter") TermConditionFilter filter);

    List<DropdownResponse> getListActivityStatus(@Param("filter") Filter filter);

    List<DropdownResponse> getListTermConditionType(@Param("filter") Filter filter);

    List<DropdownResponse> getListOrganization(@Param("filter") OrganizationDropdownFilter filter, @Param("userId") Long userId);

    List<DropdownResponse> getListLead(@Param("filter") OrganizationDropdownFilter filter, @Param("userId") Long userId);

    List<DropdownResponse> getListOrganizationContact(@Param("filter") CustomerContactDropdownFilter filter);

    List<DropdownResponse> getListCustomerContact(@Param("filter") CustomerContactDropdownFilter filter, @Param("userId") Long userId);

    List<DropdownResponse> getListEmployeeGroup(@Param("filter") Filter filter);

    List<DropdownResponse> getListTypeOfNetwork(@Param("filter") Filter filter);

    List<DropdownResponse> getListPartnerManagementPosition(@Param("filter") Filter filter);

    List<DropdownResponse> getListPartnerManagementIndustry(@Param("filter") Filter filter);

    List<DropdownResponse> getListClassPartnerManagement(@Param("filter") Filter filter,@Param("userId") Long userId);

    List<DropdownResponse> getListSupportTicketPipLine(@Param("filter") Filter filter,@Param("userId") Long userId);

    List<DropdownResponse> getListOrganizationGroup(@Param("filter") Filter filter);

    List<DropdownResponse> getListLeadGroup(@Param("filter") LeadGroupDropdownFilter filter, @Param("userId") Long userId);

    List<DropdownResponse> getListZone(@Param("filter") Filter filter);

    List<DropdownResponse> getListMarket(@Param("filter") MarketDropdownFilter filter);

    List<BOQDropdownResponse> getListBOQ(@Param("filter") BOQDropdownFilter filter);

    List<BOQDropdownDetailResponse> getListBOQDetail(@Param("boqId") Long boqId);

    List<BOQDropdownResponse> getListBOM(@Param("filter") BOMDropdownFilter filter);

    List<ProjectEstimationTermResponse> getListProjectEstimationTerm(@Param("filter") Filter filter);

    List<ProjectEstimationTermDetailResponse> getListProjectEstimationTermDetail(@Param("id") Long id);

    List<QuotationDropdownResponse> getListQuotation(@Param("filter") QuotationDropdownFilter filter);

    List<QuotationDropdownResponse> getListOpportunityQuotation(@Param("filter") QuotationDropdownFilter filter, @Param("userId") Long userId);

    List<QuotationDropdownResponse> getListOpportunitySaleOrder(@Param("filter") QuotationDropdownFilter filter, @Param("userId") Long userId);

    List<QuotationDropdownResponse> getListActivityCardQuotation(@Param("filter") QuotationDropdownFilter filter);

    List<DropdownResponse> getListTransferOrder(@Param("filter") Filter filter);

    List<SalesInvoiceDropdownResponse> getListSalesInvoice(@Param("filter") SalesInvoiceDropdownFilter filter, @Param("userId") Long userId);

    List<DropdownResponse> getListCMTerm(@Param("filter") Filter filter);

    List<DropdownResponse> getListBusinessActivity(@Param("filter") Filter filter);

    List<DropdownResponse> getListBusinessType(@Param("filter") Filter filter);

    List<DropdownResponse> getListKeyNegotiationIssue(@Param("filter") Filter filter);

    List<DropdownResponse> getListOpportunityStage(@Param("filter") OpportunityStageDropdownFilter filter, @Param("userId") Long userId);

    List<DropdownResponse> getListOpportunityStageProbability(@Param("filter") OpportunityStageDropdownFilter filter, @Param("userId") Long userId);

    List<DropdownResponse> getListOpportunitySource(@Param("filter") Filter filter);

    List<DropdownResponse> getListSource(@Param("filter") Filter filter);

    List<DropdownResponse> getListOpportunityActivity(@Param("filter") OpportunityStageDropdownFilter filter);

    List<DropdownResponse> getListSupportTicketActivity(@Param("filter") SupportStageDropdownFilter filter);

    List<DropdownResponse> getListSupportTicketBaseOn(@Param("filter") Filter filter);

    List<DropdownResponse> getListCountry(@Param("filter") Filter filter);

    List<DropdownResponse> getListStreet(@Param("filter") Filter filter);

    List<DropdownResponse> getListProvince(@Param("filter") Filter filter);

    List<DropdownResponse> getListDistrict(@Param("filter") DistrictDropdownFilter filter);

    List<DropdownResponse> getListCommune(@Param("filter") CommuneDropdownFilter filter);

    List<DropdownResponse> getListVillage(@Param("filter") VillageDropdownFilter filter);

    List<DropdownResponse> getListOrganizationContactFamily(@Param("filter") Filter filter);

    List<DropdownResponse> getListOrganizationContactLocation(@Param("filter") Filter filter);

    List<DropdownResponse> getListOrganizationContactCharacter(@Param("filter") Filter filter);

    List<DropdownResponse> getListOrganizationContactProgress(@Param("filter") Filter filter);

    List<DropdownResponse> getListOrganizationContactBrand(@Param("filter") Filter filter);

    List<DropdownResponse> getListOrganizationContactDescriptionType(@Param("filter") Filter filter);

    List<DropdownResponse> getListSalesTargetPeriod(@Param("filter") Filter filter);

    List<DropdownResponse> getListCompetitorBusinessType(@Param("filter") Filter filter);

    List<DropdownResponse> getListCompetitorPriority(@Param("filter") Filter filter);

    List<DropdownResponse> getListCompetitorEmployeeAmount(@Param("filter") Filter filter);

    List<DropdownResponse> getListCompetitorMainProduct(@Param("filter") Filter filter);

    List<DropdownResponse> getListCompetitorStrength(@Param("filter") Filter filter);

    List<DropdownResponse> getListCompetitorWeakness(@Param("filter") Filter filter);

    List<DropdownResponse> getListCompetitorStage(@Param("filter") Filter filter);

    List<DropdownResponse> getListCompetitorStatus(@Param("filter") Filter filter);

    List<DropdownResponse> getListQuotationDivision(@Param("filter") QuotationDivisionDropdownFilter filter);

    List<DropdownResponse> getListQuotationStatus(@Param("filter") Filter filter);

    List<DropdownResponse> getListQuotationStatusReason(@Param("filter") QuotationStatusReasonDropdownFilter filter);

    List<String> getListYears(@Param("filter") Filter filter);
}
