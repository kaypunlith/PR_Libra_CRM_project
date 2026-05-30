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
import com.ut.nlSystemAPi.model.filter.MissionFilter;
import com.ut.nlSystemAPi.model.request.MissionRequest;
import com.ut.nlSystemAPi.model.request.MissionUpdateRequest;
import com.ut.nlSystemAPi.model.request.MissionUpdateStatusRequest;
import com.ut.nlSystemAPi.service.MissionService;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/mission")
@Api(tags = "Mission")
@Timed
public class MissionController {

	@Autowired
	private MissionService missionService;

	@PostMapping("/list")
	@ApiOperation(value = "Mission (List)", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getList(@RequestBody MissionFilter filter) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return missionService.getList(filter);
	}

	@PostMapping("/find/{id}")
	@ApiOperation(value = "Find Mission by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return missionService.getOne(id);
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Mission (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> add(@RequestBody MissionRequest request, BindingResult bindingResult) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return missionService.insert(request);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Mission by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> update(@RequestBody MissionUpdateRequest request, BindingResult bindingResult) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return missionService.update(request);
	}

	@PostMapping("/delete/{id}")
	@ApiOperation(value = "Delete Mission by id", notes = "Delete document Mission", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
		return missionService.delete(id);
	}

	@PostMapping(value = "/update-status", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Mission Status", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> updateStatus(@RequestBody MissionUpdateStatusRequest request, BindingResult bindingResult) {
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return missionService.updateStatus(request);
	}
}
