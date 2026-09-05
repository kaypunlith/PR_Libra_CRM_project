package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportActivity;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportActivityResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportActivityMapper {

    List<SupportActivityResponse> getList(@Param("filter") Filter filter);
    Long countList(@Param("filter") Filter filter);
    List<SupportActivityResponse> getOne(@Param("id") Long id);
    Boolean insert(@Param("activity") SupportActivity activity);
    Boolean update(@Param("activity") SupportActivity activity);
    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);
    Boolean insertEmployeeGroup(@Param("activityId") Long activityId, @Param("employeeGroupId") Long employeeGroupId);
    Boolean insertStage(@Param("activityId") Long activityId, @Param("stageId") Long stageId);
    Boolean insertTask(@Param("activityId") Long activityId, @Param("name") String name, @Param("userId") Long userId);
    Boolean deleteEmployeeGroup(@Param("activityId") Long activityId);
    Boolean deleteStage(@Param("activityId") Long activityId);
    Boolean deleteTask(@Param("activityId") Long activityId, @Param("userId") Long userId);
    List<Long> getEmployeeGroupIds(@Param("activityId") Long activityId);
    List<Long> getStageIds(@Param("activityId") Long activityId);
    List<String> getTasks(@Param("activityId") Long activityId);
}
