package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.LeadGroup.LeadGroup;
import com.ut.nlSystemAPi.model.response.Lead.LeadDetailResponse;
import com.ut.nlSystemAPi.model.response.LeadGroup.LeadGroupResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadGroupMapper {

    List<LeadGroupResponse> getList(@Param("filter") Filter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") Filter filter, @Param("userId") Long userId);

    List<LeadGroupResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insert(@Param("leadGroup") LeadGroup leadGroup);

    Boolean update(@Param("leadGroup") LeadGroup leadGroup);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insertCompany(@Param("companyId") Long companyId, @Param("leadGroupId") Long leadGroupId);

    Boolean insertEmployeeGroup(@Param("employeeGroupId") Long employeeGroupId, @Param("leadGroupId") Long leadGroupId);

    Boolean insertLead(@Param("leadId") Long leadId, @Param("leadGroupId") Long leadGroupId);

    Boolean deleteCompany(@Param("leadGroupId") Long leadGroupId);

    Boolean deleteEmployeeGroup(@Param("leadGroupId") Long leadGroupId);

    Boolean deleteLead(@Param("leadGroupId") Long leadGroupId);

    List<LeadDetailResponse> getCompany(@Param("leadGroupId") Long leadGroupId);

    List<LeadDetailResponse> getEmployeeGroup(@Param("leadGroupId") Long leadGroupId);

    List<LeadDetailResponse> getLead(@Param("leadGroupId") Long leadGroupId);
}
