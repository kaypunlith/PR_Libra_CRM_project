package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.EmployeeCalendar;
import com.ut.nlSystemAPi.model.filter.EmployeeCalendarFilter;
import com.ut.nlSystemAPi.model.response.EmployeeCalendar.EmployeeCalendarResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeCalendarMapper {

    List<EmployeeCalendarResponse> getList(@Param("filter") EmployeeCalendarFilter filter);

    Long countList(@Param("filter") EmployeeCalendarFilter filter);

    List<EmployeeCalendarResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("employeeCalendar") EmployeeCalendar employeeCalendar);

    Boolean insertDetail(@Param("employeeCalendarId") Long employeeCalendarId, @Param("marketId") Long marketId, @Param("repeatDay") String repeatDay);

    List<Long> getMarketIds(@Param("employeeCalendarId") Long employeeCalendarId);

    List<String> getRepeatDayList(@Param("employeeCalendarId") Long employeeCalendarId);

    Boolean update(@Param("employeeCalendar") EmployeeCalendar employeeCalendar);

    void deleteDetails(@Param("employeeCalendarId") Long employeeCalendarId);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
