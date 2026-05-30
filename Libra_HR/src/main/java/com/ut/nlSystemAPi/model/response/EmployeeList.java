package com.ut.nlSystemAPi.model.response;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.request.ApplyEmployeeTypesRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class EmployeeList extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String profilePhoto;

    @ApiModelProperty(position = 1)
    private String chatId;

    @ApiModelProperty(position = 3)
    private String employeeSysCode;

    @ApiModelProperty(position = 3)
    private String employeeCode;

    @ApiModelProperty(position = 3)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String nameKh;

    @ApiModelProperty(position = 4)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String nameEn;

    @ApiModelProperty(position = 5)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String dob;

    @ApiModelProperty(position = 6)
    private String email;

    @ApiModelProperty(position = 7)
    private String telephone;

    @ApiModelProperty(position = 8)
    private String address;

    @ApiModelProperty(position = 9)
    private String idCard;

    @ApiModelProperty(position = 10)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String gender;

    @ApiModelProperty(position = 11)
    private Long applyToUser;

    @ApiModelProperty(position = 11)
    private String applyToUserName;

    //Background

    @ApiModelProperty(position = 12)
    private String aboutMe;

    @ApiModelProperty(position = 12)
    private String remark;

    @ApiModelProperty(position = 13)
    private String workExperience;

    @ApiModelProperty(position = 13)
    private String reference;

    @ApiModelProperty(position = 14)
    private String fileDocument;

    @ApiModelProperty(position = 15)
    private String filePath;

    //Payroll Info
    @ApiModelProperty(position = 16)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long group;

    @ApiModelProperty(position = 16)
    private String groupName;

    @ApiModelProperty(position = 17)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long departmentId;

    @ApiModelProperty(position = 17)
    private String departmentName;

    @ApiModelProperty(position = 18)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long positionId;

    @ApiModelProperty(position = 18)
    private String positionName;

    @ApiModelProperty(position = 19)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String dateOfWork;

    @ApiModelProperty(position = 20)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Float currentSalary;

    @ApiModelProperty(position = 20)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Float salarySecondWorkshift;
    
    @ApiModelProperty(position = 20)
    private Long currentSalaryStatus;

    @ApiModelProperty(position = 20)
    private String payrollDate;

    @ApiModelProperty(position = 20)
    private Float allowance;

    @ApiModelProperty(position = 21)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long maritalId;

    @ApiModelProperty(position = 21)
    private String maritalName;

    @ApiModelProperty(position = 22)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String paidType;

    @ApiModelProperty(position = 23)
    private String accountNumber;

    @ApiModelProperty(position = 24)
    private String accountId;

    @ApiModelProperty(position = 25)
    private Long donate;

    @ApiModelProperty(position = 25)
    private String providenceFund;

    // status 1 , 0 Frontend need to show in String
    @ApiModelProperty(position = 26)
    private String deposit;

    @ApiModelProperty(position = 27)
    private Float cashAmount;

    @ApiModelProperty(position = 28)
    private Long numOfMonth;

    @ApiModelProperty(position = 29)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String employeeStatusId;

    @ApiModelProperty(position = 26, hidden = true)
    private Float beginningDeposit;

    @ApiModelProperty(position = 28, hidden = true)
    private Long beginningDepositMonth;

    @ApiModelProperty(position = 26,hidden = true)
    private Float beginningProFund;

    @ApiModelProperty(position = 28, hidden = true)
    private Long beginningProFundMonth;

    @ApiModelProperty(position = 29)
    private String employeeStatusName;

    @ApiModelProperty(position = 29)
    private String reason;

    @ApiModelProperty(position = 29)
    private Long filedType;

    @ApiModelProperty(position = 30)
    private String date;

    @ApiModelProperty(position = 31)
    private String dateTo;

    @ApiModelProperty(position = 32)
    private String payroll;

    @ApiModelProperty(position = 32)
    private Boolean isSaved;

    @ApiModelProperty(position = 102)
    private String createdByName;

    @ApiModelProperty(position = 104)
    private String modifiedByName;

    @ApiModelProperty(position = 105)
    private Long employeeTypeId;

    @ApiModelProperty(position = 106, hidden = true)
    private String employeeTypeName;

    @ApiModelProperty(position = 107)
    private String employeeTypeDateFrom;

    @ApiModelProperty(position = 108)
    private String employeeTypeDateTo;

    @ApiModelProperty(position = 372)
    private Float dayRate;

    @ApiModelProperty(position = 372)
    private String bankImage;

    @ApiModelProperty(position = 372)
    private String bankImageName;

    @ApiModelProperty(position = 372)
    private Float weekendRate;

    @ApiModelProperty(position = 372)
    private String empCodeQr;

    @ApiModelProperty(position = 372)
    private Float nightRate;

    @ApiModelProperty(position = 372)
    private Float publicHolidayRate;

    @ApiModelProperty(position = 34)
    private List<EmployeeDocumentResponse> employeeDocuments;

    @ApiModelProperty(position = 35)
    private List<EmployeeStatusHistory> employeeStatusHistoryList;

    @ApiModelProperty(position = 36)
    private List<EmployeePositionHistory> employeePositionHistoryList;

    @ApiModelProperty(position = 200)
    private List<EmployeeDepartmentHistory> employeeDepartmentHistoryList;

    @ApiModelProperty(position = 37)
    private List<EmployeeSalaryHistory> employeeSalaryHistoryList;

    @ApiModelProperty(position = 38)
    private List<ApplyEmployeeTypesRequest> applyEmployeeTypesRequests;

    @ApiModelProperty(position = 361)
    private List<EmployeeWorkshiftResponse> workShifts;

    @ApiModelProperty(position = 371)
    private List<EmployeeSecondWorkshiftResponse> secondWorkShifts;

    @ApiModelProperty(position = 372)
    private List<EmployeeWorkingLocationResponse> workingLocations;

    @ApiModelProperty(position = 375)
    private List<EmployeeConnectedDeviceResponse> deviceResponses;

    @ApiModelProperty(position = 375)
    private List<EmployeeTableShiftRequestResponse> taleShiftRequests;

}
