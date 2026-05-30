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
import com.ut.nlSystemAPi.model.filter.OtRequestFilter;
import com.ut.nlSystemAPi.model.request.OtRequestRequest;
import com.ut.nlSystemAPi.model.request.OtRequestUpdateRequest;
import com.ut.nlSystemAPi.model.request.OtRequestUpdateStatusRequest;
import com.ut.nlSystemAPi.service.OtRequestService;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/ot-request")
@Api(tags = "OT Request")
@Timed
public class OtRequestController {

	@Autowired
	private OtRequestService otRequestService;

	@PostMapping("/list")
	@ApiOperation(value = "OT Request (List)", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getList(@RequestBody OtRequestFilter filter) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return otRequestService.getList(filter);
	}

	@PostMapping("/find/{id}")
	@ApiOperation(value = "Find OT Request by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return otRequestService.getOne(id);
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "OT Request (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> add(@RequestBody OtRequestRequest request, BindingResult bindingResult) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		// Check Validation
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return otRequestService.insert(request);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update OT Request by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> update(@RequestBody OtRequestUpdateRequest request, BindingResult bindingResult) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		// Check Validation
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return otRequestService.update(request);
	}

	@PostMapping(value = "/update-status", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update status by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> updateStatus(@RequestBody OtRequestUpdateStatusRequest request, BindingResult bindingResult) {
		// Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		// Check Validation
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return otRequestService.updateStatus(request);
	}

	@PostMapping("/delete/{id}")
	@ApiOperation(value = "Delete OT Request by id", notes = "Delete document OT Request", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
		return otRequestService.delete(id);
	}
}
