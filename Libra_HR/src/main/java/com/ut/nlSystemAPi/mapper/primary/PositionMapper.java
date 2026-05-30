package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.request.PositionOrderingRequest;
import com.ut.nlSystemAPi.model.request.PositionRequest;
import com.ut.nlSystemAPi.model.request.PositionUpdateRequest;
import com.ut.nlSystemAPi.model.response.PositionResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionMapper {

    List<PositionResponse> getList(@Param("filter") Filter filter);

    List<PositionResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("positionRequest") PositionRequest positionRequest);

    Boolean update(@Param("positionUpdateRequest") PositionUpdateRequest positionUpdateRequest);

    Boolean updateOrdering(@Param("positionOrderingRequest") PositionOrderingRequest positionOrderingRequest);

    Boolean delete (@Param("id") Long id, @Param("userId") Long userId);
}

