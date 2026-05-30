package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ActivityLog;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.ActivityFilter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogMapper {

  List<ActivityLog> getList(@Param("filter") ActivityFilter filter);

  Long countList(@Param("filter") Filter filter);

  List<ActivityLog> getOne(@Param("id") Long id);

  Boolean insert(@Param("activityLog") ActivityLog activityLog);

  Long getModuleId(@Param("moduleName") String moduleName);

  String getFullName(@Param("userId") Long userId);

}