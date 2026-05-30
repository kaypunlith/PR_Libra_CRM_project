package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Lead.Lead;
import com.ut.nlSystemAPi.model.entity.Lead.LeadActivityCard;
import com.ut.nlSystemAPi.model.entity.Lead.LeadDivision;
import com.ut.nlSystemAPi.model.filter.LeadActivityCardFilter;
import com.ut.nlSystemAPi.model.filter.LeadFilter;
import com.ut.nlSystemAPi.model.response.Lead.LeadActivityCardResponse;
import com.ut.nlSystemAPi.model.response.Lead.LeadDetailResponse;
import com.ut.nlSystemAPi.model.response.Lead.LeadResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadMapper {

    List<LeadResponse> getList(@Param("filter") LeadFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") LeadFilter filter, @Param("userId") Long userId);

    List<LeadResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insert(@Param("lead") Lead lead);

    Boolean update(@Param("lead") Lead lead);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insertCustomerCompanies(@Param("lead") Lead lead);

    Boolean insertCustomerCgroups(@Param("lead") Lead lead);

    Boolean insertCustomerContact(@Param("lead") Lead lead);

    Boolean insertDivisionInformation(@Param("division") LeadDivision division);

    Boolean insertDivisionInformationDetail(@Param("division") LeadDivision division);

    Boolean deleteCustomerCompanies(@Param("leadId") Long leadId);

    Boolean deleteCustomerCgroups(@Param("leadId") Long leadId);

    Boolean deleteCustomerContacts(@Param("leadId") Long leadId);

    Boolean deleteDivisionInformation(@Param("leadId") Long leadId);

    Boolean deleteDivisionInformationDetail(@Param("leadId") Long leadId);

    List<LeadDetailResponse> getCustomerCompanies(@Param("leadId") Long leadId);

    List<LeadDetailResponse> getCustomerCgroups(@Param("leadId") Long leadId);

    List<LeadDetailResponse> getCustomerContact(@Param("leadId") Long leadId);

    List<LeadDetailResponse> getDivisionInformation(@Param("leadId") Long leadId);

    List<LeadDetailResponse> getDivisionInformationDetail(@Param("divisionId") Long divisionId);

    List<LeadDetailResponse> getCustomerContracts(@Param("leadId") Long leadId);

    List<LeadDetailResponse> getCustomerTerminates(@Param("leadId") Long leadId);

    List<LeadDetailResponse> getCustomerSurveys(@Param("leadId") Long leadId);

    Boolean addActivityCard(@Param("activityCard") LeadActivityCard activityCard);

    Boolean updateLeadContactPosition(@Param("leadContactId") Long leadContactId, @Param("position") String position);

    List<LeadActivityCardResponse> getListActivityCard(@Param("filter") LeadActivityCardFilter filter, @Param("userId") Long userId);

    Long countListActivityCard(@Param("filter") LeadActivityCardFilter filter, @Param("userId") Long userId);

    List<LeadActivityCardResponse> getOneActivityCard(@Param("id") Long id, @Param("userId") Long userId);

    List<LeadResponse> getConvert(@Param("id") Long id, @Param("userId") Long userId);

    Boolean convert(@Param("lead") Lead lead);

    Boolean convertLeadContact(@Param("leadContactId") Long leadContactId, @Param("leadId") Long leadId);

    Boolean insertCustomerContracts(@Param("lead") Lead lead);

    Boolean insertCustomerTerminates(@Param("lead") Lead lead);

    Boolean insertCustomerSurveys(@Param("lead") Lead lead);

    Boolean deleteCustomerContracts(@Param("leadId") Long leadId);

    Boolean deleteCustomerTerminates(@Param("leadId") Long leadId);

    Boolean deleteCustomerSurveys(@Param("leadId") Long leadId);

    String getBusinessActivityAbbr(@Param("id") Long id);
}
