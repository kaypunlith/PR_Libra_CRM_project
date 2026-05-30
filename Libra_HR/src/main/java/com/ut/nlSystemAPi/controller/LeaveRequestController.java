package com.ut.nlSystemAPi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.LeaveRequestFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LeaveReportDetailFilter;
import com.ut.nlSystemAPi.model.request.LeaveRequestAdd;
import com.ut.nlSystemAPi.model.request.LeaveRequestUpdate;
import com.ut.nlSystemAPi.service.LeaveRequestService;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/leave-request")
@Api(tags = "Leave Request")
@Timed
public class LeaveRequestController {

	@Autowired
	private LeaveRequestService leaveRequestService;

	@PostMapping("/list")
	@ApiOperation(value = "Leave Request (List)",  authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getList(@RequestBody LeaveRequestFilter filter) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return leaveRequestService.getList(filter);
	}

	@PostMapping("/list-report-detail")
	@ApiOperation(value = "Leave Request (List)",  authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getReportDetail(@RequestBody LeaveReportDetailFilter filter) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return leaveRequestService.getReportDetail(filter);
	}

	@PostMapping("/find/{id}")
	@ApiOperation(value = "Find user by id",  authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return leaveRequestService.getOne(id);
	}

	@PostMapping("/view-detail")
	@ApiOperation(value = "View Detail",  authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getViewDetail(@RequestParam Long employeeId, @RequestParam String year) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return leaveRequestService.getViewDetail(employeeId, year);
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Leave Request (Add)",  authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> add(@RequestBody LeaveRequestAdd leaveRequestAdd, BindingResult bindingResult) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		// Check Validation
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return leaveRequestService.insert(leaveRequestAdd);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Leave Request by id",  authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> update(@RequestBody LeaveRequestUpdate leaveRequestUpdate, BindingResult bindingResult) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		// Check Validation
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return leaveRequestService.update(leaveRequestUpdate);
	}

	@PostMapping("/delete/{id}")
	@ApiOperation(value = "Delete Leave Request by id", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return leaveRequestService.delete(id);
	}

}











