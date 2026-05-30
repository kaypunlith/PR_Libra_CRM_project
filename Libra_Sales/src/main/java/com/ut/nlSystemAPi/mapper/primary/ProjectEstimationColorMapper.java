package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.ProjectEstimationColor.ProjectEstimationColor;
import com.ut.nlSystemAPi.model.filter.ProjectEstimationFilter;
import com.ut.nlSystemAPi.model.response.ProjectEstimationColor.ProjectEstimationColorResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectEstimationColorMapper {

    List<ProjectEstimationColorResponse> getList(@Param("filter") ProjectEstimationFilter filter);

    Long countList(@Param("filter") Filter filter);

    List<ProjectEstimationColorResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("color") ProjectEstimationColor color);

    Boolean update(@Param("color") ProjectEstimationColor color);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}