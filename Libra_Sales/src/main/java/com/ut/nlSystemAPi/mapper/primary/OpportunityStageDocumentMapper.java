package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Opportunities.OpportunityStageDocument;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityStageDocumentResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpportunityStageDocumentMapper {

    List<OpportunityStageDocumentResponse> getList(@Param("crmOpportunityId") Long crmOpportunityId);

    List<OpportunityStageDocumentResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("document") OpportunityStageDocument document);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

}
