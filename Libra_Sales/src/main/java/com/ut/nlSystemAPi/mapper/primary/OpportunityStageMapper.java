package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Opportunities.OpportunityStage;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityStageResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityStageMapper {

    List<OpportunityStageResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<OpportunityStageResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("stage") OpportunityStage stage);

    Boolean update(@Param("stage") OpportunityStage stage);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);
}
