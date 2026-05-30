package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.AboutUs.AboutUs;
import com.ut.nlSystemAPi.model.response.AboutUs.AboutUsResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutUsMapper {

    List<AboutUsResponse> getList(@Param("filter") Filter filter);

    Boolean update(@Param("aboutUs") AboutUs aboutUs);
}
