package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Opportunities.OpportunityTask;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityTaskResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityTaskMapper {

    List<OpportunityTaskResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<OpportunityTaskResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("task") OpportunityTask task);

    Boolean update(@Param("task") OpportunityTask task);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);
}
