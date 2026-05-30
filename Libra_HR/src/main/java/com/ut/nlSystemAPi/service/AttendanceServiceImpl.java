package com.ut.nlSystemAPi.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.AttendanceMapper;
import com.ut.nlSystemAPi.mapper.primary.WorkShiftMapper;
import com.ut.nlSystemAPi.model.Attendance;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.AttendanceFilter;
import com.ut.nlSystemAPi.model.request.AttendanceUpdateRequest;
import com.ut.nlSystemAPi.model.request.InsertAttendanceRequest;
import com.ut.nlSystemAPi.model.response.AttendanceResponse;
import com.ut.nlSystemAPi.model.response.WorkShiftResponse;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private AttendanceMapper attendanceMapper;

	@Autowired
	private MessageService messageService;

	@Autowired
	private WorkShiftMapper workShiftMapper;

	@Autowired
	private UserService userService;

	// * Get list of attendance
	@Override
	public ResponseMessage<BaseResult> getList(AttendanceFilter filter) {
		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());
		if (filter != null) {
			filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
		}

		// * Total count when filter
		if (pagination.getPage() == 1) {
			pagination.setTotal(attendanceMapper.getCount(filter));
		}
		// * Fetch data
		List<AttendanceResponse> attendancesResponse = this.attendanceMapper.getList(filter);
		attendancesResponse.forEach(attendace -> {
			if (attendace.getTotalWorkingMinute() != null && attendace.getTotalWorkingMinute() > 0) {
				long hours = attendace.getTotalWorkingMinute() / 60;
				long minutes = attendace.getTotalWorkingMinute() % 60;
				String duration = "";
				if (hours > 0) {
					duration += hours + "hrs ";
				}
				if (minutes > 0) {
					duration += minutes + "mins";
				}
				attendace.setWorkDuration(duration.trim());
			}
		});
		return ResponseMessageUtils.makeResponse(true, messageService.message("success", attendancesResponse, pagination, true));
	}

	@Override
	public ResponseMessage<BaseResult> insert(InsertAttendanceRequest request) {
		try {
			Long userId = this.userService.getUserAuth().getId();

			List<WorkShiftResponse> workShiftResponses = this.workShiftMapper.getOne(request.getWorkShiftId());
			if (workShiftResponses.isEmpty()) {
				return ResponseMessageUtils.makeResponse(true, messageService.message("WorkShift not found", false));
			}
			WorkShiftResponse workShift = workShiftResponses.get(0);

			LocalDateTime datetimeScan = parseDateTime(request.getDatetimeScan());
			if (datetimeScan == null) {
				datetimeScan = LocalDateTime.now();
			}

			// * Calculate total working minute when check out
			long totalWorkingMinute = 0;
			if (request.getType() == 2) { // * 2 is Check Out
				String dateStr = datetimeScan.toLocalDate().toString();
				AttendanceResponse checkIn = this.attendanceMapper.getCheckInAttendance(request.getEmployeeId(), dateStr);
				if (checkIn != null && checkIn.getDate() != null && checkIn.getTime() != null) {
					LocalDateTime checkInTime = LocalDateTime.parse(checkIn.getDate() + " " + checkIn.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					java.time.Duration duration = java.time.Duration.between(checkInTime, datetimeScan);
					totalWorkingMinute = duration.toMinutes();
				}
			}

			Attendance attendance = new Attendance();
			attendance.setEmployeeId(request.getEmployeeId());
			attendance.setWorkingLocationId(request.getWorkingLocationId());
			attendance.setType(request.getType());
			attendance.setStatus(request.getStatus());
			attendance.setMinute(request.getMinute());
			attendance.setWorkShiftId(request.getWorkShiftId());
			attendance.setShiftTimeFrom(workShift.getTimeFrom());
			attendance.setShiftTimeTo(workShift.getTimeTo());
			attendance.setLateCheckInTime(workShift.getLateCheckingTime());
			attendance.setLeaveEarlyTime(workShift.getLeaveEarlyTime());
			attendance.setBeginningCheckIn(workShift.getBeginningCheckIn());
			attendance.setEndingCheckIn(workShift.getEndingCheckIn());
			attendance.setBeginningCheckOut(workShift.getBeginningCheckOut());
			attendance.setEndingCheckOut(workShift.getEndingCheckOut());
			attendance.setTotalWorkingMinute(totalWorkingMinute);
			attendance.setDay(datetimeScan.toLocalDate().toString());
			attendance.setDatetimeScan(datetimeScan);
			attendance.setCreatedBy(userId);
			attendance.setIsActive(1);

			if (request instanceof AttendanceUpdateRequest) {
				attendance.setModifiedBy(userId);
			}

			Boolean inserted = this.attendanceMapper.insert(attendance);

			if (Boolean.TRUE.equals(inserted)) {
				return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
			}
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseMessageUtils.makeResponse(true, messageService.message("Error", false));
		}
	}

	@Override
	public ResponseMessage<BaseResult> getOne(Long id) {
		List<AttendanceResponse> response = this.attendanceMapper.getOne(id);
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", response, true));
	}

	@Override
	public ResponseMessage<BaseResult> delete(Long id) {
		Long userId = this.userService.getUserAuth().getId();

		Boolean result = this.attendanceMapper.delete(id, userId);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
	}

	@Override
	public ResponseMessage<BaseResult> update(AttendanceUpdateRequest request) {
		Long userId = this.userService.getUserAuth().getId();

		// * Archive old record
		this.attendanceMapper.delete(request.getId(), userId);

		// * Create new record
		return this.insert(request);
	}

	private LocalDateTime parseDateTime(String dateTime) {
		if (dateTime == null || dateTime.isEmpty()) {
			return null;
		}
		try {
			return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		} catch (DateTimeParseException exception) {
			try {
				return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			} catch (DateTimeParseException innerException) {
				try {
					return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				} catch (DateTimeParseException finalException) {
					return null;
				}
			}
		}
	}

}
