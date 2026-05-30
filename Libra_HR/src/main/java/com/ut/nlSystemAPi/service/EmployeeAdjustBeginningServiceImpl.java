package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.FileUploadUtils;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.EmployeeAdjustBeginningMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeAdjustBeginningServiceImpl implements EmployeeAdjustBeginningService {

  @Autowired
  private EmployeeAdjustBeginningMapper employeeAdjustBeginningMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private PermissionMapper permissionMapper;

    public ResponseMessage<BaseResult> getList(EmployeeFilter filter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId,"Employee (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());

        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

        List<EmployeesResponse> employeesResponses = employeeAdjustBeginningMapper.getList(filter, userId);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeesResponses, true));
    }

    public ResponseMessage<BaseResult> listEmployeeStatus() {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<ListEmployeesStatusRespon> listEmployeesStatusRespons = employeeAdjustBeginningMapper.listEmployeeStatus();
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listEmployeesStatusRespons, true));
    }

    public ResponseMessage<BaseResult> listMaritals() {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<ListMaritalsStatusRespon> listMaritalsStatusRespons = employeeAdjustBeginningMapper.listMaritals();
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listMaritalsStatusRespons, true));
    }

    public ResponseMessage<BaseResult> getOne(Long id) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Long checkCurrentSalary = employeeAdjustBeginningMapper.checkCurrentSalary(id);
        String payrollDate = employeeAdjustBeginningMapper.payrollDate(id);

        List<EmployeeList> employees = employeeAdjustBeginningMapper.getOne(id);
        if (employees != null && employees.size() > 0) {
            for (EmployeeList employee : employees) {
                if (checkCurrentSalary > 0) {
                    employee.setCurrentSalaryStatus(1L);
                } else {
                    employee.setCurrentSalaryStatus(0L);
                }
                employee.setPayrollDate(payrollDate);

                employee.setEmployeeDocuments(employeeAdjustBeginningMapper.listDocumentEmp(employee.getId()));
                List<EmployeeSalaryHistory> employeeSalaryHistories = employee.getEmployeeSalaryHistoryList();
                if (employeeSalaryHistories != null && employeeSalaryHistories.size() > 0) {
                    for (EmployeeSalaryHistory employeeSalaryHistory : employeeSalaryHistories) {
                        float salary = employeeSalaryHistory.getSalary();
                        float increaseSalary = employeeSalaryHistory.getIncreaseSalary();
                        employeeSalaryHistory.setCurrentSalary(salary + increaseSalary);
                    }
                }
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employees, true));
    }

    public ResponseMessage<BaseResult> insert(EmployeeSetting employeeSetting, MultipartFile file, MultipartFile[] files) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (Add)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Duplicate
        if(employeeAdjustBeginningMapper.checkDuplicate(employeeSetting.getNameKh(), employeeSetting.getNameEn(), employeeSetting.getIdCard(), null) > 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Your employee's name and id card had saved already in the system. Please check the name or Id Card again.", false));
        }

        // Check Data
        employeeSetting.setIsActive(1);
        employeeSetting.setCreatedBy(userId);

        // upload Profile Photo
        if (file!= null){
            String filePhoto = FileUploadUtils.saveFileUploaded(file);
            employeeSetting.setProfilePhoto(filePhoto);
        }

        if (employeeSetting.getAllowance() == null){
            employeeSetting.setAllowance((float) 0);
        }

        Boolean result = employeeAdjustBeginningMapper.insert(employeeSetting);
        if (result) {
            // Check File Document
            if (files.length > 0) {
                for (MultipartFile multipartFile : files) {
                    String fileDocument = FileUploadUtils.saveFileUploaded(multipartFile);
                    String filePath = multipartFile.getOriginalFilename();

                    EmployeeDocument employeeDocument = new EmployeeDocument();
                    employeeDocument.setEmployeeId(employeeSetting.getId());
                    employeeDocument.setFileDocument(fileDocument);
                    employeeDocument.setFilePath(filePath);
                    employeeDocument.setCreatedBy(userId);
                    employeeAdjustBeginningMapper.insertDocument(employeeDocument);
                }
            }


            // Check data payroll
            PayrollBeginning payrollBeginning = new PayrollBeginning();
            payrollBeginning.setEmployeesId(employeeSetting.getId());
            payrollBeginning.setPositionId(employeeSetting.getPositionId());
            payrollBeginning.setPaidType(employeeSetting.getPaidType());
            payrollBeginning.setAccountNumber(employeeSetting.getAccountNumber());
            payrollBeginning.setAccountId(employeeSetting.getAccountId());
            payrollBeginning.setCurrentSalary(employeeSetting.getCurrentSalary());
            payrollBeginning.setIncreaseSalary((float) 0);
            payrollBeginning.setTotalAmount((float) 0);
            payrollBeginning.setCreatedBy(userId);
            payrollBeginning.setIsActive(1);

            // insert to payroll
            employeeAdjustBeginningMapper.insertEmpPayroll(payrollBeginning);

            // check data payroll details
            //employeeAdjustBeginningMapper.insertPayrollDetailDeposit(payrollBeginning.getId(), employeeSetting.getBeginningDeposit(), userId);
            employeeAdjustBeginningMapper.insertPayrollDetailProFund(payrollBeginning.getId(), employeeSetting.getBeginningProFund(), employeeSetting.getBeginningProFundMonth(), userId);
            employeeAdjustBeginningMapper.insertOtherPayrollDetail(payrollBeginning.getId(), employeeSetting.getBeginningProFund(), userId);

            // insert to position history and employee status history
            employeeAdjustBeginningMapper.insertPositionHistory(employeeSetting.getId(), employeeSetting.getPositionId(), userId);
            employeeAdjustBeginningMapper.insertEmpStatusHistory(employeeSetting.getId(), employeeSetting.getEmployeeStatusId(), employeeSetting.getDate(), employeeSetting.getDateTo(), employeeSetting.getReason(), userId);

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> update(EmployeeSetting employeeSetting, MultipartFile file, MultipartFile[] files) {
        //Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (Edit)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // check duplicate
        if(employeeAdjustBeginningMapper.checkDuplicate(employeeSetting.getNameKh(), employeeSetting.getNameEn(), employeeSetting.getIdCard(), employeeSetting.getId()) > 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Your employee's name " +
            "and id card had saved already in the system. Please check the name or Id Card again.", false));
        }

        // check data
        employeeSetting.setIsActive(1);
        employeeSetting.setModifiedBy(userId);
        employeeSetting.setCreatedBy(userId);

        // upload Profile Photo
        if (file!= null){
            String filePhoto = FileUploadUtils.saveFileUploaded(file);
            employeeSetting.setProfilePhoto(filePhoto);
        }

        Boolean result = employeeAdjustBeginningMapper.update(employeeSetting);
        if (result) {
            // Check File Document
            if (files.length > 0) {
                for (MultipartFile multipartFile : files) {
                    String fileDocument = FileUploadUtils.saveFileUploaded(multipartFile);
                    String filePath = multipartFile.getOriginalFilename();

                    EmployeeDocument employeeDocument = new EmployeeDocument();
                    employeeDocument.setEmployeeId(employeeSetting.getId());
                    employeeDocument.setFileDocument(fileDocument);
                    employeeDocument.setFilePath(filePath);
                    employeeDocument.setCreatedBy(userId);
                    employeeAdjustBeginningMapper.updateDocument(employeeDocument);
                }
            }

            // Check data Payroll
            PayrollBeginning payrollBeginning = new PayrollBeginning();
            payrollBeginning.setEmployeesId(employeeSetting.getId());
            payrollBeginning.setPositionId(employeeSetting.getPositionId());
            payrollBeginning.setPaidType(employeeSetting.getPaidType());
            payrollBeginning.setAccountNumber(employeeSetting.getAccountNumber());
            payrollBeginning.setAccountId(employeeSetting.getAccountId());
            payrollBeginning.setCurrentSalary(employeeSetting.getCurrentSalary());
            payrollBeginning.setIncreaseSalary((float) 0);
            payrollBeginning.setTotalAmount((float) 0);
            payrollBeginning.setIsActive(1);
            employeeSetting.setModifiedBy(userId);

            long checkEmpInPayroll = employeeAdjustBeginningMapper.checkEmpInPayroll(employeeSetting.getId());
            if (checkEmpInPayroll == 1) {
                // update payroll
                employeeAdjustBeginningMapper.updateEmpPayroll(payrollBeginning);

                // update payroll detail
                Boolean deleteOldRecord = employeeAdjustBeginningMapper.deletePayrollDetail(employeeSetting.getId());
                if (deleteOldRecord){
                    employeeAdjustBeginningMapper.updatePayrollDetailProFund(employeeSetting.getBeginningProFund(), employeeSetting.getBeginningProFundMonth(), employeeSetting.getId());
                }
            } else {
                // insert payroll
                employeeAdjustBeginningMapper.insertEmpPayroll(payrollBeginning);

                // insert payroll detail
                employeeAdjustBeginningMapper.insertPayrollDetailProFund(payrollBeginning.getId(), employeeSetting.getBeginningProFund(), employeeSetting.getBeginningProFundMonth(), userId);
                employeeAdjustBeginningMapper.insertOtherPayrollDetail(payrollBeginning.getId(), employeeSetting.getBeginningProFund(), userId);
            }

            // Check insert position history when edit position
            Long positonId = employeeAdjustBeginningMapper.checkPositionHistory(employeeSetting.getId());
            if (!positonId.equals(employeeSetting.getPositionId())) {
                employeeAdjustBeginningMapper.insertPositionHistory(employeeSetting.getId(), employeeSetting.getPositionId(), userId);
            }

            // check insert employee status when edit status employee
            Long employeeStatus = employeeAdjustBeginningMapper.checkEmpStatusHistory(employeeSetting.getId());
            if (!employeeStatus.equals(employeeSetting.getEmployeeStatusId())) {
                employeeAdjustBeginningMapper.insertEmpStatusHistory(employeeSetting.getId(), employeeSetting.getEmployeeStatusId(), employeeSetting.getDate(), employeeSetting.getDateTo(), employeeSetting.getReason(), userId);
            }

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id) {
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId, "Employee (Delete)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Boolean result = employeeAdjustBeginningMapper.delete(id, userId);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> deleteDocument(Long id) {
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId, "Employee (Delete)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Boolean result = employeeAdjustBeginningMapper.deleteDocument(id, userId);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> getListApplyEmployees(ApplyEmployeesFilter filter) {
        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());

        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

        List<ApplyEmployeesList> employeesLists = employeeAdjustBeginningMapper.getListApplyEmployees(filter);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeesLists, true));
    }

    // Insert to Employees Increase Salary
    public ResponseMessage<BaseResult> insertEmployeesIncreaseSalary(EmployeesIncreaseSalary employeesIncreaseSalary) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (Add)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Duplicate
        if(employeeAdjustBeginningMapper.checkEmpIncreaseSalary(employeesIncreaseSalary.getEmployeeId(), employeesIncreaseSalary.getDate()) > 0){
            Boolean result = employeeAdjustBeginningMapper.updateEmployeesIncreaseSalary(employeesIncreaseSalary);
            if (result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
        }

        // Check Data
        employeesIncreaseSalary.setIsActive(1);
        employeesIncreaseSalary.setCreatedBy(userId);

        Boolean result =  employeeAdjustBeginningMapper.insertEmployeesIncreaseSalary(employeesIncreaseSalary);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    // List employees Increase salary
    public ResponseMessage<BaseResult> getEmployeesIncreaseSalary(EmployeeIncreaseSalarytFilter filter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        long checkIncreaseSalary = employeeAdjustBeginningMapper.checkEmpIncreaseSalary(filter.getEmployeesId(), filter.getDate());
        if (checkIncreaseSalary == 0){
            EmployeesIncreaseSalary employeesIncreaseSalaryList = new EmployeesIncreaseSalary();
            employeesIncreaseSalaryList.setEmployeeId(filter.getEmployeesId());
            employeesIncreaseSalaryList.setDate(filter.getDate());
            employeesIncreaseSalaryList.setIncreaseSalary((float) 0);
            employeesIncreaseSalaryList.setIsActive(1);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(employeesIncreaseSalaryList), true));
        } else {
            List<EmployeesIncreaseSalary> employeesIncreaseSalaries = employeeAdjustBeginningMapper.getEmployeesIncreaseSalary(filter);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeesIncreaseSalaries, true));
        }
    }

    public ResponseMessage<BaseResult> checkEmpExisting(EmployeeExistingFilter employeeExistingFilter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Duplicate
        if(employeeAdjustBeginningMapper.checkExisting(employeeExistingFilter.getNameKh(), employeeExistingFilter.getNameEn(), employeeExistingFilter.getIdCard(), employeeExistingFilter.getId()) > 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Employee's already had on system! Please check again.", false));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        }
    }

    public ResponseMessage<BaseResult> getOldAdjustBeginningDeposit(){
        Boolean result = false;
        List<EmployeeAdjustBeginning> employeeAdjustBeginningList = employeeAdjustBeginningMapper.getOldAdjustBeginningDeposit();
        if (employeeAdjustBeginningList != null && employeeAdjustBeginningList.size() > 0){
            for (EmployeeAdjustBeginning employeeAdjustBeginning : employeeAdjustBeginningList){
                result = employeeAdjustBeginningMapper.insertOldAdjustBeginningDeposit(employeeAdjustBeginning);
            }
        }

        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> addAdjustBeginningDeposit(EmployeeAdjustBeginning employeeAdjustBeginning) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (Add)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Data
        employeeAdjustBeginning.setCreatedBy(userId);
        employeeAdjustBeginning.setModifiedBy(userId);
        employeeAdjustBeginning.setIsActive(1);

        if (employeeAdjustBeginningMapper.checkAdjustBeginningDeposit(employeeAdjustBeginning.getEmployeeId(), employeeAdjustBeginning.getDate()) > 0){
            Boolean result = employeeAdjustBeginningMapper.updateAdjustBeginningDeposit(employeeAdjustBeginning);
            if(result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } else {
            Boolean result = employeeAdjustBeginningMapper.insertAdjustBeginningDeposit(employeeAdjustBeginning);
            if(result){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        }

    }

    public ResponseMessage<BaseResult> listAdjustBeginningDepositHistory(Long id) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Employee (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        System.out.println("Find Deposit By employee");
        List<EmployeeAdjustBeginning> employeeAdjustBeginningList = employeeAdjustBeginningMapper.listAdjustBeginningDepositHistory(id);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeeAdjustBeginningList, true));
    }

}
