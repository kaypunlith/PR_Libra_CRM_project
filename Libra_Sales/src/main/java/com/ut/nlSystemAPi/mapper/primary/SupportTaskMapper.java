package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportTask;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTaskResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTaskMapper {

    List<SupportTaskResponse> getList(@Param("filter") Filter filter);
    Long countList(@Param("filter") Filter filter);
    List<SupportTaskResponse> getOne(@Param("id") Long id);
    Boolean insert(@Param("task") SupportTask task);
    Boolean update(@Param("task") SupportTask task);
    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);
}
