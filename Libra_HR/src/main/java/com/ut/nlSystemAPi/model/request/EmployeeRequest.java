package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseFile;
import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeRequest {

    @ApiModelProperty(position = 1)
    private BaseFile profilePhoto;

    @ApiModelProperty(position = 4)
    private String idCard;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 6)
    private String nameKh;

    @ApiModelProperty(position = 7)
    private Integer gender;

    @ApiModelProperty(position = 8)
    private String employeeCode;

    @ApiModelProperty(position = 9)
    private String dob;

    @ApiModelProperty(position = 10)
    private Integer age;

    @ApiModelProperty(position = 11)
    private String personalNumber;

    @ApiModelProperty(position = 12)
    private String personalEmail;

    @ApiModelProperty(position = 13)
    private String otherNumber;

    @ApiModelProperty(position = 14)
    private String email;

    @ApiModelProperty(position = 15)
    private Long positionId;

    @ApiModelProperty(position = 16)
    private String address;

    @ApiModelProperty(position = 17)
    private String telephone;

    @ApiModelProperty(position = 18)
    private String education;

    @ApiModelProperty(position = 19)
    private String workExperience;

    @ApiModelProperty(position = 20)
    private String reference;

    @ApiModelProperty(position = 21)
    private Integer isShowInSales;

    @ApiModelProperty(position = 22)
    private Integer isDelivery;

    @ApiModelProperty(position = 23)
    private Integer isCollector;

    @ApiModelProperty(position = 24)
    private Integer isAllowDiscount;

    @ApiModelProperty(position = 22)
    private String employeeSysCode;

    @ApiModelProperty(position = 23)
    private Long underSupervisorId;

    @ApiModelProperty(position = 24)
    private Long departmentId;

    @ApiModelProperty(position = 25)
    private Integer employeeStatusId;

    @ApiModelProperty(position = 26)
    private Long employeeTypeId;

    @ApiModelProperty(position = 26)
    private Long maritalId;

    @ApiModelProperty(position = 27)
    private Integer attendanceStatus;

    @ApiModelProperty(position = 28)
    private String dateOfWork;

    @ApiModelProperty(position = 28)
    private String dateFrom;

    @ApiModelProperty(position = 28)
    private String dateTo;

    @ApiModelProperty(position = 28)
    private String date;

    @ApiModelProperty(position = 28)
    private String remark;

    @ApiModelProperty(position = 28)
    private String username;

    @ApiModelProperty(position = 28)
    private String password;

    @ApiModelProperty(position = 29)
    private String reason;

    @ApiModelProperty(position = 30)
    private String empReference;

    @ApiModelProperty(position = 31)
    private Float currentSalary;

    @ApiModelProperty(position = 32)
    private Float salarySecondWorkshift;

    @ApiModelProperty(position = 33)
    private Float allowance;

    @ApiModelProperty(position = 34)
    private List<BaseFile> documents;

    @ApiModelProperty(position = 34)
    private Float dayRate;

    @ApiModelProperty(position = 35)
    private Float weekendRate;

    @ApiModelProperty(position = 36)
    private Float nightRate;

    @ApiModelProperty(position = 37)
    private Float publicHolidayRate;

    @ApiModelProperty(position = 38)
    private Integer paidType;

    @ApiModelProperty(position = 38)
    private Integer isPaid;

    @ApiModelProperty(position = 39)
    private String accountNumber;

    @ApiModelProperty(position = 40)
    private String accountId;

    @ApiModelProperty(position = 41)
    private String bankImage;

    @ApiModelProperty(position = 42)
    private String bankImageName;

    @ApiModelProperty(position = 43)
    private Integer isDonated;

    @ApiModelProperty(position = 44)
    private Integer isProvidentFund;

    @ApiModelProperty(position = 45)
    private Integer isDeposit;

    @ApiModelProperty(position = 46)
    private Integer numMonth;

    @ApiModelProperty(position = 47)
    private Float amountDeposit;

    @ApiModelProperty(position = 48)
    private String deadlineDate;

    @ApiModelProperty(position = 49)
    private String chatId;

    @ApiModelProperty(position = 50)
    private Long workShift1Ids;

    @ApiModelProperty(position = 51)
    private Long workShift2Ids;

    @ApiModelProperty(position = 52)
    private List<Long> warehouses;

    @ApiModelProperty(position = 52)
    private List<Long> salesReps;
}
