package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Market.Market;
import com.ut.nlSystemAPi.model.response.Market.MarketResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketMapper {

    List<MarketResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<MarketResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("market") Market market);

    Boolean update(@Param("market") Market market);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
