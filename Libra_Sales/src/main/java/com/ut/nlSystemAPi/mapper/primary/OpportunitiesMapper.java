package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Opportunities.Opportunity;
import com.ut.nlSystemAPi.model.entity.Opportunities.OpportunityDetail;
import com.ut.nlSystemAPi.model.filter.OpportunitiesFilter;
import com.ut.nlSystemAPi.model.response.Checklist.ChecklistItemResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityAddMoreDetailResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityLogResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityReferenceResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityStageMoveResponse;
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

    OpportunityAddMoreDetailResponse getActiveDetail(@Param("opportunityId") Long opportunityId);

    List<ChecklistItemResponse> getChecklistActivities(@Param("opportunityId") Long opportunityId, @Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId, @Param("userId") Long userId);

    List<ChecklistItemResponse> getChecklistTasks(@Param("opportunityId") Long opportunityId, @Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    OpportunityStageMoveResponse getNextStage(@Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    OpportunityStageMoveResponse getPreviousStage(@Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    OpportunityStageMoveResponse getStageInPipeline(@Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    OpportunityReferenceResponse getQuotationReference(@Param("id") Long id);

    OpportunityReferenceResponse getSalesOrderReference(@Param("id") Long id);

    Boolean updateOpportunityReference(@Param("opportunityId") Long opportunityId, @Param("quotationId") Long quotationId, @Param("salesOrderId") Long salesOrderId, @Param("customerId") Long customerId, @Param("customerContactId") Long customerContactId, @Param("amount") Double amount, @Param("userId") Long userId);

    Boolean archiveActivityDetails(@Param("opportunityId") Long opportunityId, @Param("userId") Long userId);

    Boolean archiveTaskDetails(@Param("opportunityId") Long opportunityId, @Param("userId") Long userId);

    Boolean insertActivityDetail(@Param("opportunityId") Long opportunityId, @Param("stageId") Long stageId, @Param("activityId") Long activityId, @Param("userId") Long userId);

    Boolean insertTaskDetail(@Param("opportunityId") Long opportunityId, @Param("stageId") Long stageId, @Param("taskId") Long taskId, @Param("userId") Long userId);

    List<Long> getActiveActivityIds(@Param("opportunityId") Long opportunityId);

    List<Long> getActiveTaskIds(@Param("opportunityId") Long opportunityId);

    String getActivityName(@Param("id") Long id);

    String getTaskName(@Param("id") Long id);

    Boolean updateDetailDescription(@Param("detailId") Long detailId, @Param("description") String description);

    Boolean convertOpportunityDetails(@Param("opportunityId") Long opportunityId, @Param("description") String description, @Param("userId") Long userId);

    Boolean skipOpportunityDetails(@Param("opportunityId") Long opportunityId, @Param("userId") Long userId);

    Boolean deactivateDetail(@Param("detailId") Long detailId);

    Long getPreviousConvertedDetailId(@Param("opportunityId") Long opportunityId, @Param("stageId") Long stageId);

    Boolean reopenDetail(@Param("detailId") Long detailId);

    Boolean insertLog(@Param("opportunityId") Long opportunityId, @Param("description") String description, @Param("type") Integer type, @Param("status") Integer status, @Param("userId") Long userId);

    List<OpportunityLogResponse> getListLog(@Param("opportunityId") Long opportunityId);

    Boolean touchOpportunity(@Param("opportunityId") Long opportunityId, @Param("userId") Long userId);
}
