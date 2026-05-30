package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Service.Service;
import com.ut.nlSystemAPi.model.filter.ServiceFilter;
import com.ut.nlSystemAPi.model.response.Service.ServiceResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceMapper {

    List<ServiceResponse> getList(@Param("filter") ServiceFilter filter);

    Long countList(@Param("filter") ServiceFilter filter);

    List<ServiceResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("service") Service service);

    Boolean update(@Param("service") Service service);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}