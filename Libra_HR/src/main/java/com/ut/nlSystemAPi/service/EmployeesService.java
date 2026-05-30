package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.EmployeeFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.EmployeeRequest;
import com.ut.nlSystemAPi.model.request.EmployeeResetRequest;
import com.ut.nlSystemAPi.model.request.EmployeeTerminateSessionRequest;
import com.ut.nlSystemAPi.model.request.EmployeeUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface EmployeesService {

    ResponseMessage<BaseResult> getList(EmployeeFilter filter);

    ResponseMessage<BaseResult> getOne(Long id);

    ResponseMessage<BaseResult> insert(EmployeeRequest employeeRequest);

    ResponseMessage<BaseResult> update(EmployeeUpdateRequest employeeRequest);

    ResponseMessage<BaseResult> delete(Long id);

    ResponseMessage<BaseResult> reset(EmployeeResetRequest employeeResetRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> terminateSession(EmployeeTerminateSessionRequest employeeTerminateSessionRequest);

    ResponseMessage<BaseResult> getEmployeeAchievement(Long id);

    ResponseMessage<BaseResult> getEmployeeMistake(Long id);
}
