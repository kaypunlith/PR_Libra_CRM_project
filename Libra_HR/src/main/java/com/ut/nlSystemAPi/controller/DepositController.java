package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.DepositFilter;
import com.ut.nlSystemAPi.model.DepositListFilter;
import com.ut.nlSystemAPi.model.DepositUpdateStatus;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.*;
import com.ut.nlSystemAPi.model.response.EmployeeDepositFilter;
import com.ut.nlSystemAPi.service.DepositService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deposit-request")
@Api(tags = "Deposit Request")
@Timed
public class DepositController {

	@Autowired
	private DepositService depositService;

	@PostMapping("/list")
	@ApiOperation(value = "Deposit Request (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getList(@RequestBody DepositListFilter filter) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return depositService.getList(filter);
	}

	@PostMapping("/find/{id}")
	@ApiOperation(value = "Find Deposit Request by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return depositService.getOne(id);
	}

	@PostMapping("/list-ProFund")
	@ApiOperation(value = "Deposit Request (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getListProFund(@RequestBody DepositFilter filter) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return depositService.getListProFund(filter);
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Deposit Request (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> add(@RequestBody DepositRequest depositRequest, BindingResult bindingResult) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		// * Check Validation
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return depositService.insert(depositRequest);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Deposit Request by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> update(@RequestBody DepositRequestUpdate depositRequestUpdate, BindingResult bindingResult) {
		// * Check Header Token
		System.out.println("Hello");
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		// * Check Validation
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		System.out.println("depositRequestUpdate: " + depositRequestUpdate);
		return depositService.update(depositRequestUpdate);
	}

	@PostMapping("/delete/{id}")
	@ApiOperation(value = "Delete Deposit Request by id", notes = "Delete document Deposit Request", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return depositService.delete(id);
	}

	@PostMapping(value = "/update-status-all", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Status all", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> updateStatusAll(@RequestBody DepositUpdateStatus depositUpdateStatus) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return depositService.updateStatusAll(depositUpdateStatus);
	}

	@PostMapping(value = "/update-status-byEmp", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Deposit Request by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> update(@RequestBody DepositRequestStatus depositRequestStatus, BindingResult bindingResult) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return depositService.updateStatusByEmp(depositRequestStatus);
	}

	@PostMapping("/list-title")
	@ApiOperation(value = "Title (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
	public ResponseMessage<BaseResult> getListTitle() {
		return depositService.getListTitle();
	}

	@PostMapping("/employee-list")
	@ApiOperation(value = "List employee have deposit", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getEmployeeDeposit(@RequestBody EmployeeDepositFilter filter) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return depositService.getEmployeeDeposit(filter);
	}

}
