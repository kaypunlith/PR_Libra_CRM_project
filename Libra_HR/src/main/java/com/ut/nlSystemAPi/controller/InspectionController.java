package com.ut.nlSystemAPi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.InspectionFilter;
import com.ut.nlSystemAPi.model.request.Inspection.InspectionRequest;
import com.ut.nlSystemAPi.model.request.Inspection.InspectionUpdate;
import com.ut.nlSystemAPi.service.InspectionService;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/employee-evaluation")
@Api(tags = "Employee Evaluation")
@Timed
public class InspectionController {

	@Autowired
	private InspectionService inspectionService;

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Department (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> add(@RequestBody InspectionRequest inspectionRequest) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return inspectionService.insert(inspectionRequest);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Inspection (Update)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> update(@RequestBody InspectionUpdate inspectionUpdate) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return inspectionService.update(inspectionUpdate);
	}

	@PostMapping("/delete/{id}")
	@ApiOperation(value = "Delete Inspection by id", notes = "Delete Inspection", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
		return inspectionService.delete(id);
	}

	@PostMapping("/list")
	@ApiOperation(value = "Inspection For Home Screen (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getListInspection(@RequestBody InspectionFilter filter) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return inspectionService.getList(filter);
	}

	@PostMapping("/list-summary-screen")
	@ApiOperation(value = "Inspection Summary For Home Screen (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getListSummaryInspection(@RequestBody InspectionFilter filter) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return inspectionService.getListSummaryInspection(filter);
	}

	@PostMapping("/find/{id}")
	@ApiOperation(value = "Find Inspection by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return inspectionService.getOne(id);
	}

	@PostMapping("/find-detail/{id}")
	@ApiOperation(value = "Find Inspection by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> findByIdDetail(@PathVariable("id") Long id) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return inspectionService.getInspectionOne(id);
	}

	@PostMapping("/list-group")
	@ApiOperation(value = "Inspection-Group (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
	public ResponseMessage<BaseResult> getListInspectionGroup() {
		return inspectionService.getListInspectionGroup();
	}

	@PostMapping("/list-province")
	@ApiOperation(value = "Province (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
	public ResponseMessage<BaseResult> getListProvince() {
		return inspectionService.getListProvince();
	}

	@PostMapping("/list-quater")
	@ApiOperation(value = "Quater (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
	public ResponseMessage<BaseResult> getListQuater() {
		return inspectionService.getListQuater();
	}

}
