package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.response.ApplyEmployeesFilter;
import com.ut.nlSystemAPi.service.EmployeeAdjustBeginningService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@SuppressWarnings("All")
@RestController
@RequestMapping("/adjustBeginning")
@Api(tags = "Adjust Beginning")
@Timed
public class EmployeeAdjustBeginningController {

  @Autowired
  private EmployeeAdjustBeginningService employeeAdjustBeginningService;

    @PostMapping("/list")
    @ApiOperation(value = "Employees Adjust Beginning Deposit (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody EmployeeFilter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.getList(filter);
    }

    @PostMapping("/listEmployeeStatus")
    @ApiOperation(value = "Employee Status (List)", notes = "Filed Type : type 1 : Date && type 2 : Date Range", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listEmployeeStatus() {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.listEmployeeStatus();
    }

    @PostMapping("/listMaritals")
    @ApiOperation(value = "Employee (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listMaritals() {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.listMaritals();
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Employees Adjust Beginning Deposit by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.getOne(id);
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Employees Adjust Beginning Deposit / Provident Fund (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nameKh", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "nameEn", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "dob", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "email", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "telephone", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "address", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "gender", dataType = "string", paramType = "form", value = "Male or Female", required = true),
            @ApiImplicitParam(name = "idCard", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "applyToUser", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "aboutMe", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "remark", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "reference", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "workExperience", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "departmentId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "positionId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "dateOfWork", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "currentSalary", dataType = "float", paramType = "form", required = true),
            @ApiImplicitParam(name = "allowance", dataType = "float", paramType = "form"),
            @ApiImplicitParam(name = "maritalId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "paidType", dataType = "long", paramType = "form", value = "1 : Pay by Cash , 2 : Pay by Bank" ,required = true),
            @ApiImplicitParam(name = "accountNumber", dataType = "string", paramType = "form", value = "account number of Bank"),
            @ApiImplicitParam(name = "accountId", dataType = "string", paramType = "form", value = " account id of Bank"),
            @ApiImplicitParam(name = "donate", dataType = "long", paramType = "form", value = " 1 : Yes , 0 : No"),
            @ApiImplicitParam(name = "providenceFund", dataType = "long", paramType = "form", value = " 1 : Yes , 0 : No"),
            @ApiImplicitParam(name = "deposit", dataType = "long", paramType = "form", value = " 1 : Yes , 0 : No"),
            @ApiImplicitParam(name = "cashAmount", dataType = "float", paramType = "form"),
            @ApiImplicitParam(name = "numOfMonth", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "employeeStatusId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "reason", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "date", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "dateTo", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "payroll", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "beginningDeposit", dataType = "float", paramType = "form"),
            @ApiImplicitParam(name = "beginningDepositMonth", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "beginningProFund", dataType = "float", paramType = "form"),
            @ApiImplicitParam(name = "beginningProFundMonth", dataType = "long", paramType = "form")
    })
    public ResponseMessage<BaseResult> add(@ApiIgnore EmployeeSetting employeeSetting, BindingResult bindingResult, @RequestPart(name = "file", required = false) MultipartFile file,
                                           @RequestPart(name = "files", required = false) MultipartFile[] files){
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return employeeAdjustBeginningService.insert(employeeSetting, file, files);
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Update Employees Adjust Beginning Deposit / Provident Fund by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "nameKh", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "nameEn", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "dob", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "email", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "telephone", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "address", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "gender", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "idCard", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "applyToUser", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "aboutMe", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "remark", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "reference", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "workExperience", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "departmentId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "positionId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "dateOfWork", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "currentSalary", dataType = "float", paramType = "form", required = true),
            @ApiImplicitParam(name = "allowance", dataType = "float", paramType = "form"),
            @ApiImplicitParam(name = "maritalId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "paidType", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "accountNumber", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "accountId", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "donate", dataType = "long", paramType = "form", value = " 1 : Yes , 0 : No"),
            @ApiImplicitParam(name = "providenceFund", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "deposit", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "cashAmount", dataType = "float", paramType = "form"),
            @ApiImplicitParam(name = "numOfMonth", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "employeeStatusId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "reason", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "date", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "dateTo", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "payroll", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "beginningDeposit", dataType = "float", paramType = "form"),
            @ApiImplicitParam(name = "beginningDepositMonth", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "beginningProFund", dataType = "float", paramType = "form"),
            @ApiImplicitParam(name = "beginningProFundMonth", dataType = "long", paramType = "form")
    })
    public ResponseMessage<BaseResult> update(@ApiIgnore EmployeeSetting employeeSetting, BindingResult bindingResult, @RequestPart(name = "file", required = false) MultipartFile file,
                                              @RequestPart(name = "files", required = false) MultipartFile[] files) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return employeeAdjustBeginningService.update(employeeSetting, file, files);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete Employees Adjust Beginning Deposit / Provident Fund by id", notes = "Delete document Employee", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
        return employeeAdjustBeginningService.delete(id);
    }

    @PostMapping("/delete-document/{id}")
    @ApiOperation(value = "Delete Document Employee by id", notes = "Delete document Employee", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> deleteDocument(@PathVariable("id") Long id) {
        return employeeAdjustBeginningService.deleteDocument(id);
    }

    @PostMapping("/list-applyEmployees")
    @ApiOperation(value = "List apply employees", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListApplyEmployees(@RequestBody ApplyEmployeesFilter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.getListApplyEmployees(filter);
    }

    // Update Increase Salary
    @PostMapping(value = "/increase-salary", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Employees Increase Salary (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "increaseSalary", dataType = "float", paramType = "form", required = true),
            @ApiImplicitParam(name = "date", dataType = "string", paramType = "form", required = true)
    })
    public ResponseMessage<BaseResult> insertEmployeesIncreaseSalary(@ApiIgnore EmployeesIncreaseSalary employeesIncreaseSalary){
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.insertEmployeesIncreaseSalary(employeesIncreaseSalary);
    }

    // List employeess increase salary by id
    @PostMapping("/increase-salary-list")
    @ApiOperation(value = "Employee Increase Salary (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getEmployeesIncreaseSalary(@RequestBody EmployeeIncreaseSalarytFilter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.getEmployeesIncreaseSalary(filter);
    }

    // Check employees name existing
    @PostMapping("/check-existing")
    @ApiOperation(value = "Employee Check Existing", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> checkEmpExisting(@RequestBody EmployeeExistingFilter employeeExistingFilter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.checkEmpExisting(employeeExistingFilter);
    }


    @PostMapping("/backup-adjust-beginning")
    @ApiOperation(value = "Backup Adjust Beginning Deposit", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList() {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.getOldAdjustBeginningDeposit();
    }


    @PostMapping(value = "/deposit-add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Add new Adjust Beginning Deposit", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "employeeId", dataType = "long", paramType = "form", required = true),
        @ApiImplicitParam(name = "date", dataType = "string", paramType = "form", required = true),
        @ApiImplicitParam(name = "amount", dataType = "float", paramType = "form", required = true)
    })
    public ResponseMessage<BaseResult> addAdjustBeginningDeposit(@ApiIgnore EmployeeAdjustBeginning employeeAdjustBeginning) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.addAdjustBeginningDeposit(employeeAdjustBeginning);
    }

    @PostMapping("/deposit-find/{id}")
    @ApiOperation(value = "Find Adjust Beginning Deposit by employee id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listAdjustBeginningDepositHistory(@PathVariable("id") Long id) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return employeeAdjustBeginningService.listAdjustBeginningDepositHistory(id);
    }

}
