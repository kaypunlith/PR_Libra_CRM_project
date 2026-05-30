package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.OrganizationGroup.OrganizationGroup;
import com.ut.nlSystemAPi.model.response.OrganizationGroup.OrganizationGroupDetailResponse;
import com.ut.nlSystemAPi.model.response.OrganizationGroup.OrganizationGroupResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationGroupMapper {

    List<OrganizationGroupResponse> getList(@Param("filter") Filter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") Filter filter, @Param("userId") Long userId);

    List<OrganizationGroupResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("organizationGroup") OrganizationGroup organizationGroup);

    Boolean update(@Param("organizationGroup") OrganizationGroup organizationGroup);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    List<OrganizationGroupDetailResponse> getCompany(@Param("id") Long id);

    List<OrganizationGroupDetailResponse> getEmployeeGroup(@Param("id") Long id);

    List<OrganizationGroupDetailResponse> getPriceType(@Param("id") Long id);

    List<OrganizationGroupDetailResponse> getOrganization(@Param("id") Long id);

    Boolean insertCompany(@Param("companyId") Long companyId, @Param("organizationGroupId") Long organizationGroupId);

    Boolean insertEmployeeGroup(@Param("employeeGroupId") Long employeeGroupId, @Param("organizationGroupId") Long organizationGroupId);

    Boolean insertPriceType(@Param("priceTypeId") Long priceTypeId, @Param("organizationGroupId") Long organizationGroupId);

    Boolean insertOrganization(@Param("organizationId") Long organizationId, @Param("organizationGroupId") Long organizationGroupId);

    Boolean deleteCompany(@Param("organizationGroupId") Long organizationGroupId);

    Boolean deleteEmployeeGroup(@Param("organizationGroupId") Long organizationGroupId);

    Boolean deletePriceType(@Param("organizationGroupId") Long organizationGroupId);

    Boolean deleteOrganization(@Param("organizationGroupId") Long organizationGroupId);


}