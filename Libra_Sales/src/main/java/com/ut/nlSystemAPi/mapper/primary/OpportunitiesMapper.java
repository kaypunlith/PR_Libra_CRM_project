package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Opportunities.Opportunity;
import com.ut.nlSystemAPi.model.entity.Opportunities.OpportunityDetail;
import com.ut.nlSystemAPi.model.filter.OpportunitiesFilter;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunitiesMapper {

    List<OpportunityResponse> getList(@Param("filter") OpportunitiesFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") OpportunitiesFilter filter, @Param("userId") Long userId);

    List<OpportunityResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insert(@Param("opportunity") Opportunity opportunity);

    Boolean update(@Param("opportunity") Opportunity opportunity);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertDetail(@Param("detail") OpportunityDetail detail);

    Boolean closeDetail(@Param("opportunityId") Long opportunityId, @Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    Long getSourceType(@Param("sourceId") Long sourceId);

    Boolean updateCode(@Param("id") Long id, @Param("code") String code);
}
