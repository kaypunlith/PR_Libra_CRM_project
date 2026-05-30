package com.ut.nlSystemAPi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.AttendanceFilter;
import com.ut.nlSystemAPi.model.request.AttendanceUpdateRequest;
import com.ut.nlSystemAPi.model.request.InsertAttendanceRequest;
import com.ut.nlSystemAPi.service.AttendanceService;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/attendance")
@Api(tags = "Attendance")
@Timed
@SuppressWarnings("all")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@PostMapping("/list")
	@ApiOperation(value = "Attendance list report", notes = "Status (1: On-Time; 2: Late; 3: Early); timeClock (1: Check In; 2: Check Out); Show: 1, Detail, 2: summary", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getList(@RequestBody AttendanceFilter filter) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return this.attendanceService.getList(filter);
	}

	@PostMapping("/find/{id}")
	@ApiOperation(value = "Find attendance by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return this.attendanceService.getOne(id);
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Insert attendance manually", notes = "Adds a manual attendance record based on the provided work shift and employee data", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> add(@RequestBody InsertAttendanceRequest request, BindingResult bindingResult) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return attendanceService.insert(request);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update attendance record", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> update(@RequestBody AttendanceUpdateRequest request, BindingResult bindingResult) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return attendanceService.update(request);
	}

	@PostMapping("/delete/{id}")
	@ApiOperation(value = "Delete Attendance by id", notes = "Delete document Attendance", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return this.attendanceService.delete(id);
	}

}
