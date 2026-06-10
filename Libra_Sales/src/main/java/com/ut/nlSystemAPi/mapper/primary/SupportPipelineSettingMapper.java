package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportPipelineSetting;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportPipelineSettingResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportPipelineSettingStageResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportPipelineSettingMapper {

    List<SupportPipelineSettingResponse> getList(@Param("filter") Filter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") Filter filter, @Param("userId") Long userId);

    List<SupportPipelineSettingResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("pipeline") SupportPipelineSetting pipeline);

    Boolean update(@Param("pipeline") SupportPipelineSetting pipeline);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertEmployeeGroup(@Param("pipelineId") Long pipelineId, @Param("employeeGroupId") Long employeeGroupId);

    Boolean insertStage(@Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId, @Param("percent") Double percent, @Param("ordering") Integer ordering, @Param("skippable") Integer skippable);

    Boolean insertStageActivity(@Param("stageId") Long stageId, @Param("activityId") Long activityId);

    Boolean insertActivityEmployeeGroup(@Param("activityId") Long activityId, @Param("employeeGroupId") Long employeeGroupId);

    Boolean deleteEmployeeGroup(@Param("pipelineId") Long pipelineId);

    Boolean deleteStage(@Param("pipelineId") Long pipelineId);

    Boolean deleteStageActivity(@Param("stageId") Long stageId);

    List<SupportPipelineSettingStageResponse> getStages(@Param("pipelineId") Long pipelineId);

    List<Long> getStageActivityIds(@Param("stageId") Long stageId);
}
