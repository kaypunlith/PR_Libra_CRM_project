package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.StockLevel;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.StockLevel.StockLevelResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockLevelMapper {

    List<StockLevelResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<StockLevelResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("stockLevel") StockLevel stockLevel);

    Boolean update(@Param("stockLevel") StockLevel stockLevel);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);
}
