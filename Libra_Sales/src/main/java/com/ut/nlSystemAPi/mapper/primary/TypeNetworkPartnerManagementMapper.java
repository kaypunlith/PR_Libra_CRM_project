package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.TypeNetworkPartnerManagement.TypeNetworkPartnerManagement;
import com.ut.nlSystemAPi.model.response.TypeNetworkPartnerManagement.TypeNetworkPartnerManagementResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeNetworkPartnerManagementMapper {
    List<TypeNetworkPartnerManagementResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<TypeNetworkPartnerManagementResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("typeNetworkPartnerManagement") TypeNetworkPartnerManagement typeNetworkPartnerManagement);

    Boolean update(@Param("typeNetworkPartnerManagement") TypeNetworkPartnerManagement typeNetworkPartnerManagement);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
