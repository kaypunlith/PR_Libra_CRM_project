package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ChartAccountGroup;
import com.ut.nlSystemAPi.model.ChartAccountType;
import com.ut.nlSystemAPi.model.base.Filter;

import com.ut.nlSystemAPi.model.filter.ChartAccountGroupFilter;

import com.ut.nlSystemAPi.model.filter.AccountTypeFilter;

import com.ut.nlSystemAPi.model.response.ChartAccountGroup.ChartAccountGroupResponse;
import com.ut.nlSystemAPi.model.response.ChartAccountType.ChartAccountTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChartAccountGroupMapper {


    List<ChartAccountGroupResponse> getList(@Param("filter") ChartAccountGroupFilter filter);


    Long countList(@Param("filter") ChartAccountGroupFilter filter);

    List<ChartAccountGroupResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("chartAccountGroup") ChartAccountGroup chartAccountGroup);

    Boolean update(@Param("chartAccountGroup") ChartAccountGroup chartAccountGroup);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean updateStatus(@Param("chartAccountGroup") ChartAccountGroup chartAccountGroup);
}
