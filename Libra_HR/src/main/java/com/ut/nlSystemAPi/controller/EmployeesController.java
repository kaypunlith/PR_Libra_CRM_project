package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.EmployeeFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.EmployeeRequest;
import com.ut.nlSystemAPi.model.request.EmployeeResetRequest;
import com.ut.nlSystemAPi.model.request.EmployeeTerminateSessionRequest;
import com.ut.nlSystemAPi.model.request.EmployeeUpdateRequest;
import com.ut.nlSystemAPi.service.EmployeesService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/employee")
@Api(tags = "Employee")
@Timed
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @PostMapping("/list")
    @ApiOperation(value = "Employee (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody EmployeeFilter filter) {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeesService.getList(filter);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Employee by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeesService.getOne(id);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Employees (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody EmployeeRequest employeeRequest, BindingResult bindingResult) {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return employeesService.insert(employeeRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Employee by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody EmployeeUpdateRequest employeeRequest, BindingResult bindingResult) {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return employeesService.update(employeeRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete Employee by id", notes = "Delete Employee", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeesService.delete(id);
    }

    @PostMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Reset employee username/password by employee id", notes = "Reset username/password", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> reset(@RequestBody EmployeeResetRequest employeeResetRequest, BindingResult bindingResult,
                                             HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeesService.reset(employeeResetRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/terminate-session")
    @ApiOperation(value = "Terminate Employee Session", notes = "Terminate employee session", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> terminateSession(@RequestBody EmployeeTerminateSessionRequest employeeTerminateSessionRequest) {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeesService.terminateSession(employeeTerminateSessionRequest);
    }

    @PostMapping("/achievement-list/{id}")
    @ApiOperation(value = "List Employee Achievement by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getEmployeeAchievement(@PathVariable("id") Long id) {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeesService.getEmployeeAchievement(id);
    }

    @PostMapping("/mistake-list/{id}")
    @ApiOperation(value = "List Employee Mistake by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getEmployeeMistake(@PathVariable("id") Long id) {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeesService.getEmployeeMistake(id);
    }
}
