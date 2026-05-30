package com.ut.nlSystemAPi.model.response;

import com.ut.nlSystemAPi.model.base.BaseFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EmployeesResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long userId;

    @ApiModelProperty(position = 2)
    private BaseFile profilePhoto;

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 2)
    private String photoName;

    @ApiModelProperty(position = 3)
    private String username;

    @ApiModelProperty(position = 4)
    private String code;

    @ApiModelProperty(position = 4)
    private String employeeCode;

    @ApiModelProperty(position = 5)
    private String nameKh;

    @ApiModelProperty(position = 6)
    private String name;

    @ApiModelProperty(position = 7)
    private Long gender;

    @ApiModelProperty(position = 8)
    private String dob;

    @ApiModelProperty(position = 8)
    private Integer age;

    @ApiModelProperty(position = 9)
    private String telephone;

    @ApiModelProperty(position = 9)
    private String email;

    @ApiModelProperty(position = 9)
    private String personalNumber;

    @ApiModelProperty(position = 9)
    private String personalEmail;

    @ApiModelProperty(position = 9)
    private String otherNumber;

    @ApiModelProperty(position = 9)
    private String education;

    @ApiModelProperty(position = 9)
    private String workExperience;

    @ApiModelProperty(position = 9)
    private String reference;

    @ApiModelProperty(position = 9)
    private Integer isShowInSales;

    @ApiModelProperty(position = 9)
    private String employeeSysCode;

    @ApiModelProperty(position = 9)
    private Long underSupervisorId;

    @ApiModelProperty(position = 9)
    private String deadlineDate;

    @ApiModelProperty(position = 9)
    private Integer attendanceStatus;

    @ApiModelProperty(position = 10)
    private String idCard;

    @ApiModelProperty(position = 11)
    private Long positionId;

    @ApiModelProperty(position = 12)
    private String positionName;

    @ApiModelProperty(position = 13)
    private Long departmentId;

    @ApiModelProperty(position = 13)
    private Long donate;

    @ApiModelProperty(position = 13)
    private Long providentFund;

    @ApiModelProperty(position = 13)
    private Long deposit;

    @ApiModelProperty(position = 13)
    private Integer numMonth;

    @ApiModelProperty(position = 13)
    private Float amountDeposit;

    @ApiModelProperty(position = 14)
    private String departmentName;

    @ApiModelProperty(position = 15)
    private String dateOfWork;

    @ApiModelProperty(position = 15)
    private String dateFrom;

    @ApiModelProperty(position = 15)
    private String dateTo;

    @ApiModelProperty(position = 15)
    private Long maritalId;

    @ApiModelProperty(position = 15)
    private String maritalName;

    @ApiModelProperty(position = 16)
    private Long applyUserStatus;

    @ApiModelProperty(position = 17)
    private String address;

    @ApiModelProperty(position = 17)
    private String remark;

    @ApiModelProperty(position = 19)
    private Long payroll;

    @ApiModelProperty(position = 19)
    private String date;

    @ApiModelProperty(position = 20)
    private Long employeeStatusId;

    @ApiModelProperty(position = 20)
    private String employeeStatusName;

    @ApiModelProperty(position = 20)
    private Long employeeTypeId;

    @ApiModelProperty(position = 20)
    private String employeeTypeName;

    @ApiModelProperty(position = 20)
    private String reason;

    @ApiModelProperty(position = 20)
    private String empReference;

    @ApiModelProperty(position = 21)
    private Float salary;

    @ApiModelProperty(position = 21)
    private Float currentSalary;

    @ApiModelProperty(position = 21)
    private Float salarySecondWorkshift;

    @ApiModelProperty(position = 22)
    private Float increase;

    @ApiModelProperty(position = 23)
    private Float allowance;

    @ApiModelProperty(position = 24)
    private Long paidType;

    @ApiModelProperty(position = 24)
    private Long isPaid;

    @ApiModelProperty(position = 24)
    private String accountNumber;

    @ApiModelProperty(position = 24)
    private String accountId;

    @ApiModelProperty(position = 24)
    private String bankImage;

    @ApiModelProperty(position = 24)
    private String bankImageName;

    @ApiModelProperty(position = 24)
    private Float dayRate;

    @ApiModelProperty(position = 24)
    private Float weekendRate;

    @ApiModelProperty(position = 24)
    private Float nightRte;

    @ApiModelProperty(position = 24)
    private Float nightRate;

    @ApiModelProperty(position = 24)
    private Float publicHolidayRate;

    @ApiModelProperty(position = 25)
    private Boolean isSaved;

    @ApiModelProperty(position = 102)
    private String created;

    @ApiModelProperty(position = 102)
    private String createdBy;

    @ApiModelProperty(position = 102)
    private String modified;

    @ApiModelProperty(position = 102)
    private String modifiedSalaryDate;

    @ApiModelProperty(position = 104)
    private String modifiedBy;

    @ApiModelProperty(position = 104)
    private Long status;

    @ApiModelProperty(position = 104)
    private Long numberOfDayEmployeeType;

    @ApiModelProperty(position = 104)
    private String chatId;

    @ApiModelProperty(position = 104)
    private String sysCode;

    @ApiModelProperty(position = 104)
    private String qrCode;

    @ApiModelProperty(position = 55)
    private List<EmployeeWorkshiftResponse> workShifts;

    @ApiModelProperty(position = 56)
    private List<EmployeeSecondWorkshiftResponse> secondWorkShifts;

    @ApiModelProperty(position = 56)
    private Long workShift1Ids;

    @ApiModelProperty(position = 56)
    private String workShift1Name;

    @ApiModelProperty(position = 56)
    private Long workShift2Ids;

    @ApiModelProperty(position = 56)
    private String workShift2Name;

    @ApiModelProperty(position = 57)
    private List<EmployeeDocumentResponse> employeeDocuments;

    @ApiModelProperty(position = 57)
    private List<BaseFile> documents;

    @ApiModelProperty(position = 58)
    private List<EmployeeDocumentResponse> warehouses;

    @ApiModelProperty(position = 59)
    private List<EmployeeDocumentResponse> salesReps;

    @ApiModelProperty(position = 58)
    private List<EmployeeConnectedDeviceResponse> deviceResponses;

    @ApiModelProperty(position = 59)
    private List<EmployeePositionHistory> employeePositionHistoryList;

    @ApiModelProperty(position = 60)
    private List<EmployeeDepartmentHistory> employeeDepartmentHistoryList;

}
