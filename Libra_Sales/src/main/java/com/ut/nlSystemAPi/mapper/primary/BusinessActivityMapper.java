package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.BusinessActivity.BusinessActivity;
import com.ut.nlSystemAPi.model.response.BusinessActivity.BusinessActivityResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessActivityMapper {

    List<BusinessActivityResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<BusinessActivityResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("businessActivity") BusinessActivity businessActivity);

    Boolean update(@Param("businessActivity") BusinessActivity businessActivity);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}