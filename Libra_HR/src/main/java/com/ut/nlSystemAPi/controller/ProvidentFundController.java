package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.ProvidentFundFilter;
import com.ut.nlSystemAPi.model.ProvidentFundListFilter;
import com.ut.nlSystemAPi.model.ProvidentFundUpdateStatus;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequest;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequestStatus;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequestUpdate;
import com.ut.nlSystemAPi.model.response.EmployeeDepositFilter;
import com.ut.nlSystemAPi.service.ProvidentFundService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provident-fund")
@Api(tags = "Provident Fund")
@Timed
public class ProvidentFundController {

	@Autowired
	private ProvidentFundService providentFundService;

	@PostMapping("/list")
	@ApiOperation(value = "Provident Fund (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getList(@RequestBody ProvidentFundListFilter filter) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return providentFundService.getList(filter);
	}

	@PostMapping("/find/{id}")
	@ApiOperation(value = "Find Provident Fund by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return providentFundService.getOne(id);
	}

	@PostMapping("/list-ProFund")
	@ApiOperation(value = "Provident Fund (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getListProFund(@RequestBody ProvidentFundFilter filter) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return providentFundService.getListProFund(filter);
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Provident Fund (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> add(@RequestBody ProvidentFundRequest providentFundRequest, BindingResult bindingResult) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		// * Check Validation
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return providentFundService.insert(providentFundRequest);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Provident Fund by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> update(@RequestBody ProvidentFundRequestUpdate providentFundRequestUpdate, BindingResult bindingResult) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		// * Check Validation
		if (bindingResult.hasErrors()) {
			return ResponseMessageUtils.makeResponse(false, bindingResult);
		}
		return providentFundService.update(providentFundRequestUpdate);
	}

	@PostMapping("/delete/{id}")
	@ApiOperation(value = "Delete Provident Fund by id", notes = "Delete document Provident Fund", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
		return providentFundService.delete(id);
	}

	@PostMapping(value = "/update-status-all", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Status all", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> updateStatusAll(@RequestBody ProvidentFundUpdateStatus providentFundUpdateStatus) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return providentFundService.updateStatusAll(providentFundUpdateStatus);
	}

	@PostMapping(value = "/update-status-byEmp", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Provident Fund by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> update(@RequestBody ProvidentFundRequestStatus providentFundRequestUpdate, BindingResult bindingResult) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return providentFundService.updateStatusByEmp(providentFundRequestUpdate);
	}

	@PostMapping("/list-title")
	@ApiOperation(value = "Title (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
	public ResponseMessage<BaseResult> getListTitle() {
		return providentFundService.getListTitle();
	}

	@PostMapping("/employee-list")
	@ApiOperation(value = "List employee have deposit", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
	public ResponseMessage<BaseResult> getEmployeeProvidentFund(@RequestBody EmployeeDepositFilter filter) {
		// * Check Header Token
		if (UserAuthSession.getUserAuth() == null) {
			return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
		}
		return providentFundService.getEmployeeProvidentFund(filter);
	}
}
