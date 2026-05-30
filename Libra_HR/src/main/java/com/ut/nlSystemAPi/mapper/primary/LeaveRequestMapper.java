package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.LeaveRequest;
import com.ut.nlSystemAPi.model.LeaveRequestFilter;
import com.ut.nlSystemAPi.model.filter.LeaveReportDetailFilter;
import com.ut.nlSystemAPi.model.response.LeaveReportDetailResponse;
import com.ut.nlSystemAPi.model.response.LeaveRequestDetailResponse;
import com.ut.nlSystemAPi.model.response.LeaveRequestResponse;

@Repository
public interface LeaveRequestMapper {

	List<LeaveRequestResponse> getList(@Param("filter") LeaveRequestFilter filter);

	int count(@Param("filter") LeaveRequestFilter filter);

	List<LeaveRequestResponse> getOne(@Param("id") Long id);

	List<LeaveRequestResponse> getViewDetail(@Param("employeeId") String employeeId, @Param("year") String year);

	Boolean insert(@Param("leaveRequest") LeaveRequest leaveRequest);

	Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

	List<LeaveRequestDetailResponse> getLeaveRequestDetail(@Param("employeeId") String employeeId, @Param("year") String year);

	List<LeaveReportDetailResponse> getReportDetail(@Param("filter") LeaveReportDetailFilter filter);

	Long getUerIdByEmployeeId(@Param("employeeId") Long employeeId);

}
