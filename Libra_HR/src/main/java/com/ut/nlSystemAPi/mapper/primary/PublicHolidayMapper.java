package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.PublicHoliday;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.PublicHolidayFilter;
import com.ut.nlSystemAPi.model.request.PositionRequest;
import com.ut.nlSystemAPi.model.request.PositionUpdateRequest;
import com.ut.nlSystemAPi.model.response.PositionResponse;
import com.ut.nlSystemAPi.model.response.PublicHolidayResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicHolidayMapper {

    List<PublicHolidayResponse> getList(@Param("filter") PublicHolidayFilter filter);

    List<PublicHolidayResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("publicHoliday") PublicHoliday publicHoliday);

    Boolean update(@Param("publicHoliday") PublicHoliday publicHoliday);

    Boolean delete (@Param("id") Long id, @Param("userId") Long userId);
}

