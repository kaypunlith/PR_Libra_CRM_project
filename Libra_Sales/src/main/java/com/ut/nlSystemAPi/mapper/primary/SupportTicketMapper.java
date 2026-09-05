package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportTicket;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportTicketDetail;
import com.ut.nlSystemAPi.model.filter.SupportTicketFilter;
import com.ut.nlSystemAPi.model.response.Checklist.ChecklistItemResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketAddMoreDetailResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketAddMoreItemResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketAddMoreStageResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketLogResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketMapper {

    List<SupportTicketResponse> getList(@Param("filter") SupportTicketFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") SupportTicketFilter filter, @Param("userId") Long userId);

    List<SupportTicketResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insert(@Param("support") SupportTicket support);

    Boolean update(@Param("support") SupportTicket support);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertDetail(@Param("detail") SupportTicketDetail detail);

    Boolean closeDetail(@Param("supportId") Long supportId, @Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    Boolean updateCode(@Param("id") Long id, @Param("code") String code);

    Double getStagePercent(@Param("stageId") Long stageId);

    Boolean insertEmployeeGroup(@Param("supportId") Long supportId, @Param("employeeGroupId") Long employeeGroupId);

    Boolean deleteEmployeeGroup(@Param("supportId") Long supportId);

    SupportTicketAddMoreDetailResponse getAddMoreDetail(@Param("supportId") Long supportId);

    SupportTicketAddMoreStageResponse getStageByOrdering(@Param("pipelineId") Long pipelineId, @Param("ordering") Long ordering);

    SupportTicketAddMoreStageResponse getNextStage(@Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    SupportTicketAddMoreStageResponse getPreviousStage(@Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    SupportTicketAddMoreStageResponse getStageInPipeline(@Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    List<SupportTicketAddMoreStageResponse> getSkipStages(@Param("pipelineId") Long pipelineId, @Param("ordering") Long ordering, @Param("userId") Long userId);

    List<SupportTicketAddMoreItemResponse> getAddMoreActivities(@Param("supportId") Long supportId, @Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId, @Param("userId") Long userId);

    List<SupportTicketAddMoreItemResponse> getAddMoreTasks(@Param("supportId") Long supportId, @Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    List<ChecklistItemResponse> getChecklistActivities(@Param("supportId") Long supportId, @Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId, @Param("userId") Long userId);

    List<ChecklistItemResponse> getChecklistTasks(@Param("supportId") Long supportId, @Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);

    String getSupportTicketChecklistData(@Param("supportId") Long supportId, @Param("stageId") Long stageId);

    Integer updateSupportTicketChecklist(@Param("supportId") Long supportId, @Param("stageId") Long stageId, @Param("checkedData") String checkedData, @Param("userId") Long userId);

    Boolean insertSupportTicketChecklist(@Param("supportId") Long supportId, @Param("stageId") Long stageId, @Param("checkedData") String checkedData, @Param("userId") Long userId);

    List<SupportTicketLogResponse> getListLog(@Param("supportId") Long supportId);

    List<Long> getActiveActivityIds(@Param("supportId") Long supportId);

    List<Long> getActiveTaskIds(@Param("supportId") Long supportId);

    Boolean archiveActivityDetails(@Param("supportId") Long supportId);

    Boolean archiveTaskDetails(@Param("supportId") Long supportId);

    Boolean insertActivityDetail(@Param("supportId") Long supportId, @Param("stageId") Long stageId, @Param("activityId") Long activityId, @Param("userId") Long userId);

    Boolean insertTaskDetail(@Param("supportId") Long supportId, @Param("stageId") Long stageId, @Param("taskId") Long taskId, @Param("userId") Long userId);

    String getActivityName(@Param("id") Long id);

    String getTaskName(@Param("id") Long id);

    Boolean updateDetailDescription(@Param("detailId") Long detailId, @Param("description") String description);

    Boolean convertSupportDetails(@Param("supportId") Long supportId, @Param("description") String description, @Param("userId") Long userId);

    Boolean skipSupportDetails(@Param("supportId") Long supportId, @Param("userId") Long userId);

    Boolean deactivateDetail(@Param("detailId") Long detailId);

    Long getPreviousConvertedDetailId(@Param("supportId") Long supportId, @Param("stageId") Long stageId);

    Boolean reopenDetail(@Param("detailId") Long detailId);

    Boolean insertLog(@Param("supportId") Long supportId, @Param("description") String description, @Param("type") Integer type, @Param("status") Integer status, @Param("userId") Long userId);

    Boolean touchSupport(@Param("supportId") Long supportId, @Param("userId") Long userId);
}
