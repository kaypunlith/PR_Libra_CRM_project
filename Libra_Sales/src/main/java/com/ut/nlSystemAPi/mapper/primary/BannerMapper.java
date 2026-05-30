package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Banner.Banner;
import com.ut.nlSystemAPi.model.response.Banner.BannerResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerMapper {

    List<BannerResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<BannerResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("banner") Banner banner);

    Boolean update(@Param("banner") Banner banner);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

}
