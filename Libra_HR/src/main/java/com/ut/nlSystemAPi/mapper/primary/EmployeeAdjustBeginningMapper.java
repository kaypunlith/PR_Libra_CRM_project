package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeAdjustBeginningMapper {

    List<EmployeesResponse> getList(@Param("filter") EmployeeFilter filter, @Param("userId") Long userId);

    List<EmployeeList> getOne(@Param("id") Long id);

    List<ListEmployeesStatusRespon>listEmployeeStatus();

    List<ListMaritalsStatusRespon>listMaritals();

    List<EmployeeDocumentResponse> listDocumentEmp(@Param("empId") Long empId);

    Long checkDuplicate(@Param("nameKh") String nameKh, @Param("nameEn") String nameEn, @Param("idCard") String idCard, @Param("id") Long id);

    Long checkExisting(@Param("nameKh") String nameKh, @Param("nameEn") String nameEn, @Param("idCard") String idCard, @Param("id") Long id);

    Boolean insert(@Param("employeeSetting") EmployeeSetting employeeSetting);

    Long checkEmpInPayroll(@Param("employeesId") Long employeesId);

    Boolean insertEmpPayroll(@Param("payrollBeginning") PayrollBeginning payrollBeginning);

    Boolean insertPayrollDetailDeposit(@Param("payrollId") Long payrollId, @Param("amount") Float amount, @Param("userId") Long userId);

    Boolean insertPayrollDetailProFund(@Param("payrollId") Long payrollId, @Param("amount") Float amount, @Param("monthOfProFund") Long monthOfProFund, @Param("userId") Long userId);

    Boolean insertOtherPayrollDetail(@Param("payrollId") Long payrollId, @Param("amount") Float amount, @Param("userId") Long userId);

    Boolean deletePayrollDetail(@Param("employeeId") Long employeeId);

    Boolean updateEmpPayroll(@Param("payrollBeginning") PayrollBeginning payrollBeginning);

    Boolean updatePayrollDetailDeposit(@Param("amount") Float amount, @Param("employeeId") Long employeeId);

    Boolean updatePayrollDetailProFund(@Param("amount") Float amount, @Param("monthOfProFund") Long monthOfProFund, @Param("employeeId") Long employeeId);

    Boolean insertDocument(@Param("employeeDocument") EmployeeDocument employeeDocument);

    Boolean update(@Param("employeeSetting") EmployeeSetting employeeSetting);

    Long checkPositionHistory(@Param("employeesId") Long employeesId);

    Long checkEmpStatusHistory(@Param("employeesId") Long employeesId);

    Boolean insertPositionHistory(@Param("employeesId") Long employeesId, @Param("positionId") Long positionId, @Param("createdBy") Long createdBy);

    Boolean insertEmpStatusHistory(@Param("employeesId") Long employeesId, @Param("employeeStatusId") Long employeeStatusId,@Param("date") String date, @Param("dateTo") String dateTo, @Param("reason") String reason, @Param("createdBy") Long createdBy);

    Boolean updateDocument(@Param("employeeDocument") EmployeeDocument employeeDocument);

    Long checkCurrentSalary(@Param("employeesId") Long employeesId);

    String payrollDate(@Param("employeesId") Long employeesId);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean deleteDocument(@Param("id") Long id, @Param("userId") Long userId);

    List<ApplyEmployeesList> getListApplyEmployees(@Param("filter") ApplyEmployeesFilter filter);

    // Insert to employees increase salary
    Boolean insertEmployeesIncreaseSalary(@Param("employeesIncreaseSalary")EmployeesIncreaseSalary employeesIncreaseSalary);

    Boolean updateEmployeesIncreaseSalary(@Param("employeesIncreaseSalary")EmployeesIncreaseSalary employeesIncreaseSalary);

    Long checkEmpIncreaseSalary(@Param("employeesId") Long employeesId, @Param("date") String date);

    List<EmployeesIncreaseSalary> getEmployeesIncreaseSalary(@Param("filter") EmployeeIncreaseSalarytFilter filter);

    List<EmployeeAdjustBeginning> getOldAdjustBeginningDeposit();

    Boolean insertOldAdjustBeginningDeposit(@Param("employeeAdjustBeginning") EmployeeAdjustBeginning employeeAdjustBeginning);

    // adjust beginning
    Long checkAdjustBeginningDeposit(@Param("employeeId") Long employeeId, @Param("date") String date);

    Boolean insertAdjustBeginningDeposit(@Param("employeeAdjustBeginning") EmployeeAdjustBeginning employeeAdjustBeginning);

    Boolean updateAdjustBeginningDeposit(@Param("employeeAdjustBeginning") EmployeeAdjustBeginning employeeAdjustBeginning);

    List<EmployeeAdjustBeginning> listAdjustBeginningDepositHistory(@Param("id") Long id);

}

