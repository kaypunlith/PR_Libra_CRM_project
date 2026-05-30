package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.BusinessType.BusinessType;
import com.ut.nlSystemAPi.model.response.BusinessType.BusinessTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessTypeMapper {

    List<BusinessTypeResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<BusinessTypeResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("businessType") BusinessType businessType);

    Boolean update(@Param("businessType") BusinessType businessType);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}