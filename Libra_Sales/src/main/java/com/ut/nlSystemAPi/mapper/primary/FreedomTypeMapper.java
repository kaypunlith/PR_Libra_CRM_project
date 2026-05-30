package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.FreedomType.FreedomType;
import com.ut.nlSystemAPi.model.response.FreedomType.FreedomTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreedomTypeMapper {

    List<FreedomTypeResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<FreedomTypeResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("freedomType") FreedomType freedomType);

    Boolean update(@Param("freedomType") FreedomType freedomType);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
