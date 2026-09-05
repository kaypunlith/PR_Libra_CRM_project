package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportStage;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportStageResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportStageMapper {

    List<SupportStageResponse> getList(@Param("filter") Filter filter);
    Long countList(@Param("filter") Filter filter);
    List<SupportStageResponse> getOne(@Param("id") Long id);
    Boolean insert(@Param("stage") SupportStage stage);
    Boolean update(@Param("stage") SupportStage stage);
    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);
}
