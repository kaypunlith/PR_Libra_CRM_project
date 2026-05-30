package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.response.ApplyEmployeesFilter;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeAdjustBeginningService {

    ResponseMessage<BaseResult> getList(EmployeeFilter filter);

    ResponseMessage<BaseResult> getOne(Long id);

    ResponseMessage<BaseResult> listEmployeeStatus();

    ResponseMessage<BaseResult> listMaritals();

    ResponseMessage<BaseResult> checkEmpExisting(EmployeeExistingFilter employeeExistingFilter);

    ResponseMessage<BaseResult> insert(EmployeeSetting employeeSetting, MultipartFile file, MultipartFile[] files);

    ResponseMessage<BaseResult> update(EmployeeSetting employeeSetting, MultipartFile file, MultipartFile[] files);

    ResponseMessage<BaseResult> delete(Long id);

    ResponseMessage<BaseResult> deleteDocument(Long id);

    ResponseMessage<BaseResult> getListApplyEmployees(ApplyEmployeesFilter filter);

    ResponseMessage<BaseResult> insertEmployeesIncreaseSalary(EmployeesIncreaseSalary employeesIncreaseSalary);

    ResponseMessage<BaseResult> getEmployeesIncreaseSalary(EmployeeIncreaseSalarytFilter filter);

    ResponseMessage<BaseResult> getOldAdjustBeginningDeposit();

    ResponseMessage<BaseResult> addAdjustBeginningDeposit(EmployeeAdjustBeginning employeeAdjustBeginning);

    ResponseMessage<BaseResult> listAdjustBeginningDepositHistory(Long id);
}