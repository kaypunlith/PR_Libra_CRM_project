package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.ClassPartnerManagement.ClassPartnerManagement;
import com.ut.nlSystemAPi.model.response.ClassPartnerManagement.ClassPartnerManagementResponse;
import com.ut.nlSystemAPi.model.response.ClassPartnerManagement.ClassPartnerManagementTypeNetworkEmployeeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassPartnerManagementMapper {
    List<ClassPartnerManagementResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<ClassPartnerManagementResponse> getOne(@Param("id") Long id);

    List<Long> getEgroupIds(@Param("classId") Long classId);

    List<ClassPartnerManagementTypeNetworkEmployeeResponse> getTypeNetworkEmployees(@Param("classId") Long classId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("classPartnerManagement") ClassPartnerManagement classPartnerManagement);

    Boolean update(@Param("classPartnerManagement") ClassPartnerManagement classPartnerManagement);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    void deleteEgroups(@Param("classId") Long classId);

    void deleteTypeNetworkEmployees(@Param("classId") Long classId);

    void insertEgroup(@Param("classId") Long classId, @Param("egroupId") Long egroupId);

    void insertTypeNetworkEmployee(@Param("classId") Long classId, @Param("typeNetworkId") Long typeNetworkId, @Param("userId") Long userId);
}
