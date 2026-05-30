package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.ChartAccountType;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.ChartAccountType.ChartAccountTypeResponse;

@Repository
public interface ChartAccountTypeMapper {

    List<ChartAccountTypeResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<ChartAccountTypeResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("chartAccountType") ChartAccountType chartAccountType);

    Boolean update(@Param("chartAccountType") ChartAccountType chartAccountType);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean updateStatus(@Param("chartAccountType") ChartAccountType chartAccountType);
}
