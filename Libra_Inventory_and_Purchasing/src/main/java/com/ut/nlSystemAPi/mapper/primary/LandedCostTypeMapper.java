package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.LandedCost.LandedCostResponse;
import com.ut.nlSystemAPi.model.response.LandedCostType.LandedCostTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandedCostTypeMapper {

  List<LandedCostTypeResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<LandedCostTypeResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("landedCostType") LandedCostType landedCostType);

  Boolean update(@Param("landedCostType") LandedCostType landedCostType);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

}