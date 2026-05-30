package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Organization.Organization;
import com.ut.nlSystemAPi.model.entity.Organization.OrganizationActivityCard;
import com.ut.nlSystemAPi.model.entity.Organization.OrganizationDivision;
import com.ut.nlSystemAPi.model.filter.OrganizationActivityCardFilter;
import com.ut.nlSystemAPi.model.filter.OrganizationFilter;
import com.ut.nlSystemAPi.model.response.Dropdown.DropdownResponse;
import com.ut.nlSystemAPi.model.response.Organization.OrganizationActivityCardResponse;
import com.ut.nlSystemAPi.model.response.Organization.OrganizationDetailResponse;
import com.ut.nlSystemAPi.model.response.Organization.OrganizationResponse;
import com.ut.nlSystemAPi.model.response.Organization.OrganizationZoneResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrganizationMapper {

    List<OrganizationResponse> getList(@Param("filter") OrganizationFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") OrganizationFilter filter, @Param("userId") Long userId);

    OrganizationActivityCardResponse countListActivityCard(@Param("organizationId") Long organizationId);

    List<OrganizationActivityCardResponse> getListActivityCard(@Param("filter") OrganizationActivityCardFilter filter);

    Boolean addActivityCard(@Param("activityCard") OrganizationActivityCard activityCard);

    List<OrganizationResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insert(@Param("organization") Organization organization);

    Boolean update(@Param("organization") Organization organization);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertCustomerCompanies(@Param("organization") Organization organization);

    Boolean insertCustomerCgroups(@Param("organization") Organization organization);

    Boolean insertCustomerContact(@Param("organization") Organization organization);

    Boolean insertCustomerContracts(@Param("organization") Organization organization);

    Boolean insertCustomerTerminates(@Param("organization") Organization organization);

    Boolean insertCustomerSurveys(@Param("organization") Organization organization);

    Boolean insertDivisionInformation(@Param("division") OrganizationDivision division);

    Boolean insertDivisionInformationDetail(@Param("division") OrganizationDivision division);

    Boolean deleteCustomerCompanies(@Param("organizationId") Long organizationId);

    Boolean deleteCustomerCgroups(@Param("organizationId") Long organizationId);

    Boolean deleteCustomerContacts(@Param("organizationId") Long organizationId);

    Boolean deleteCustomerContracts(@Param("organizationId") Long organizationId);

    Boolean deleteCustomerTerminates(@Param("organizationId") Long organizationId);

    Boolean deleteCustomerSurveys(@Param("organizationId") Long organizationId);

    Boolean deleteDivisionInformation(@Param("organizationId") Long organizationId);

    Boolean deleteDivisionInformationDetail(@Param("organizationId") Long organizationId);

    List<OrganizationDetailResponse> getCustomerCompanies(@Param("organizationId") Long organizationId);

    List<OrganizationDetailResponse> getCustomerCgroups(@Param("organizationId") Long organizationId);

    List<OrganizationDetailResponse> getCustomerContact(@Param("organizationId") Long organizationId);

    List<OrganizationDetailResponse> getCustomerContracts(@Param("organizationId") Long organizationId);

    List<OrganizationDetailResponse> getCustomerTerminates(@Param("organizationId") Long organizationId);

    List<OrganizationDetailResponse> getCustomerSurveys(@Param("organizationId") Long organizationId);

    List<OrganizationDetailResponse> getDivisionInformation(@Param("organizationId") Long organizationId);

    List<OrganizationDetailResponse> getDivisionInformationDetail(@Param("divisionId") Long division);

    List<OrganizationZoneResponse> getListZone();

    String getBusinessActivityAbbr(@Param("id") Long id);

    Map<String, Object> getLocationAddressParts(@Param("streetId") Long streetId,
                                                @Param("provinceId") Long provinceId,
                                                @Param("districtId") Long districtId,
                                                @Param("communeId") Long communeId,
                                                @Param("villageId") Long villageId);

    Integer getIsFreedom(@Param("id") Long id);
    
    Integer getIsSync(@Param("id") Long id);

    Boolean updateCustomerContact(@Param("newId") Long newId, @Param("oldId") Long oldId);

    Boolean markCustomerSyncedToFreedom(@Param("id") Long id, @Param("userId") Long userId);

}
