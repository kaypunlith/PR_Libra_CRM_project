package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Opportunities.PipelineSetting;
import com.ut.nlSystemAPi.model.response.Opportunities.PipelineSettingResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.PipelineSettingStageResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineSettingMapper {

    List<PipelineSettingResponse> getList(@Param("filter") Filter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") Filter filter, @Param("userId") Long userId);

    List<PipelineSettingResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("pipeline") PipelineSetting pipeline);

    Boolean update(@Param("pipeline") PipelineSetting pipeline);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertEmployeeGroup(@Param("pipelineId") Long pipelineId, @Param("employeeGroupId") Long employeeGroupId);

    Boolean insertStage(@Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId, @Param("percent") Double percent, @Param("ordering") Integer ordering, @Param("skippable") Integer skippable);

    Boolean insertStageActivity(@Param("stageId") Long stageId, @Param("activityId") Long activityId);

    Boolean insertActivityEmployeeGroup(@Param("activityId") Long activityId, @Param("employeeGroupId") Long employeeGroupId);

    Boolean deleteEmployeeGroup(@Param("pipelineId") Long pipelineId);

    Boolean deleteStage(@Param("pipelineId") Long pipelineId);

    Boolean deleteStageActivity(@Param("stageId") Long stageId);

    List<PipelineSettingStageResponse> getStages(@Param("pipelineId") Long pipelineId);

    List<Long> getStageActivityIds(@Param("stageId") Long stageId);
}
