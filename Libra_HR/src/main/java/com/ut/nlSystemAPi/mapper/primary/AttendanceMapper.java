package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.Attendance;
import com.ut.nlSystemAPi.model.filter.AttendanceFilter;
import com.ut.nlSystemAPi.model.response.AttendanceResponse;

@Repository
public interface AttendanceMapper {

	List<AttendanceResponse> getList(@Param("filter") AttendanceFilter filter);

	Long getCount(@Param("filter") AttendanceFilter filter);

	Boolean insert(@Param("attendance") Attendance attendance);

	List<AttendanceResponse> getOne(@Param("id") Long id);

	AttendanceResponse getCheckInAttendance(@Param("employeeId") Long employeeId, @Param("date") String date);

	Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
