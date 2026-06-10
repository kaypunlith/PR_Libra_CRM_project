package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportTicket;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportTicketDetail;
import com.ut.nlSystemAPi.model.filter.SupportTicketFilter;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketMapper {

    List<SupportTicketResponse> getList(@Param("filter") SupportTicketFilter filter, @Param("userId") Long userId);
    Long countList(@Param("filter") SupportTicketFilter filter, @Param("userId") Long userId);
    List<SupportTicketResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);
    Boolean insert(@Param("support") SupportTicket support);
    Boolean update(@Param("support") SupportTicket support);
    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
    Boolean insertDetail(@Param("detail") SupportTicketDetail detail);
    Boolean closeDetail(@Param("supportId") Long supportId, @Param("pipelineId") Long pipelineId, @Param("stageId") Long stageId);
    Boolean updateCode(@Param("id") Long id, @Param("code") String code);
    Double getStagePercent(@Param("stageId") Long stageId);
    Boolean insertEmployeeGroup(@Param("supportId") Long supportId, @Param("employeeGroupId") Long employeeGroupId);
    Boolean deleteEmployeeGroup(@Param("supportId") Long supportId);
}
