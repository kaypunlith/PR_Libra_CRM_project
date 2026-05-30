package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.PartnerManagement.PartnerManagement;
import com.ut.nlSystemAPi.model.response.PartnerManagement.PartnerManagementResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerManagementMapper {
    List<PartnerManagementResponse> getList(@Param("filter") Filter filter, @Param("classId") Long classId, @Param("typeNetworkId") Long typeNetworkId, @Param("userId") Long userId);

    Long countList(@Param("filter") Filter filter, @Param("classId") Long classId, @Param("typeNetworkId") Long typeNetworkId, @Param("userId") Long userId);

    List<PartnerManagementResponse> getOne(@Param("id") Long id);

    Long checkDuplicateTel(@Param("keyContactTel") String keyContactTel, @Param("id") Long id);

    Boolean insert(@Param("partnerManagement") PartnerManagement partnerManagement);

    Boolean update(@Param("partnerManagement") PartnerManagement partnerManagement);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
