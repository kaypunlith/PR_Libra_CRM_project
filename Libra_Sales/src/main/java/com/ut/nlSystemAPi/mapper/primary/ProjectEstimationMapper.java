package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.ProjectEstimation.ProjectEstimation;
import com.ut.nlSystemAPi.model.entity.ProjectEstimation.ProjectEstimationDetail;
import com.ut.nlSystemAPi.model.filter.ProjectEstimationFilter;
import com.ut.nlSystemAPi.model.response.ProjectEstimation.ProjectEstimationDetailResponse;
import com.ut.nlSystemAPi.model.response.ProjectEstimation.ProjectEstimationResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectEstimationMapper {

    List<ProjectEstimationResponse> getList(@Param("filter") ProjectEstimationFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") ProjectEstimationFilter filter, @Param("userId") Long userId);

    List<ProjectEstimationResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insert(@Param("projectEstimation") ProjectEstimation projectEstimation);

    Boolean insertDetail(@Param("detail") ProjectEstimationDetail detail);

    List<ProjectEstimationDetailResponse> getListDetail(@Param("projectEstimationId") Long projectEstimationId);

    Boolean approve(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

}