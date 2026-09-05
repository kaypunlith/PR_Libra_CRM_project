package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.response.EmployeeSecondWorkshiftResponse;
import com.ut.nlSystemAPi.model.response.EmployeeWorkshiftResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Employee extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long userId;

    @ApiModelProperty(position = 2)
    private Long userHrId;

    @ApiModelProperty(position = 2)
    private List<Long> employeeGroupId;


    @ApiModelProperty(position = 3)
    private String photo;

    @ApiModelProperty(position = 4)
    private String photoName;

    @ApiModelProperty(position = 5)
    private String idCard;

    @ApiModelProperty(position = 6)
    private String name;

    @ApiModelProperty(position = 7)
    private String nameKh;

    @ApiModelProperty(position = 7)
    private String username;

    @ApiModelProperty(position = 7)
    private String password;

    @ApiModelProperty(position = 8)
    private Integer gender;

    @ApiModelProperty(position = 9)
    private String code;

    @ApiModelProperty(position = 10)
    private String dob;

    @ApiModelProperty(position = 11)
    private Integer age;

    @ApiModelProperty(position = 12)
    private String personalNumber;

    @ApiModelProperty(position = 13)
    private String personalEmail;

    @ApiModelProperty(position = 14)
    private String otherNumber;

    @ApiModelProperty(position = 15)
    private String email;

    @ApiModelProperty(position = 16)
    private Long positionId;

    @ApiModelProperty(position = 17)
    private String address;

    @ApiModelProperty(position = 18)
    private String telephone;

    @ApiModelProperty(position = 19)
    private String education;

    @ApiModelProperty(position = 20)
    private String workExperience;

    @ApiModelProperty(position = 21)
    private String reference;

    @ApiModelProperty(position = 22)
    private Integer isShowInSales;

    @ApiModelProperty(position = 23)
    private Integer isDelivery;

    @ApiModelProperty(position = 24)
    private Integer isCollector;

    @ApiModelProperty(position = 24)
    private Integer isAllowDiscount;

    @ApiModelProperty(position = 23)
    private String employeeSysCode;

    @ApiModelProperty(position = 24)
    private Long underSupervisorId;

    @ApiModelProperty(position = 25)
    private Long departmentId;

    @ApiModelProperty(position = 26)
    private Integer employeeStatusId;

    @ApiModelProperty(position = 27)
    private Long employeeTypeId;

    @ApiModelProperty(position = 27)
    private String employeeTypeDateFrom;

    @ApiModelProperty(position = 27)
    private String employeeTypeDateTo;

    @ApiModelProperty(position = 28)
    private Integer attendanceStatus;

    @ApiModelProperty(position = 29)
    private String remark;

    @ApiModelProperty(position = 26)
    private Long maritalId;

    @ApiModelProperty(position = 30)
    private String reason;

    @ApiModelProperty(position = 28)
    private String dateOfWork;

    @ApiModelProperty(position = 28)
    private String dateFrom;

    @ApiModelProperty(position = 28)
    private String dateTo;

    @ApiModelProperty(position = 28)
    private String date;

    @ApiModelProperty(position = 31)
    private String empReference;

    @ApiModelProperty(position = 32)
    private Float currentSalary;

    @ApiModelProperty(position = 33)
    private Float salarySecondWorkshift;

    @ApiModelProperty(position = 34)
    private Float allowance;

    @ApiModelProperty(position = 35)
    private Float dayRate;

    @ApiModelProperty(position = 36)
    private Float weekendRate;

    @ApiModelProperty(position = 37)
    private Float nightRate;

    @ApiModelProperty(position = 38)
    private Float publicHolidayRate;

    @ApiModelProperty(position = 39)
    private Integer paidBy;

    @ApiModelProperty(position = 39)
    private Integer isPaid;

    @ApiModelProperty(position = 40)
    private String accountNumber;

    @ApiModelProperty(position = 41)
    private String accountId;

    @ApiModelProperty(position = 42)
    private String bankImage;

    @ApiModelProperty(position = 43)
    private String bankImageName;

    @ApiModelProperty(position = 44)
    private Integer isDonated;

    @ApiModelProperty(position = 45)
    private Integer isProvidentFund;

    @ApiModelProperty(position = 46)
    private Integer isDeposit;

    @ApiModelProperty(position = 47)
    private Integer numMonth;

    @ApiModelProperty(position = 48)
    private Float amountDeposit;

    @ApiModelProperty(position = 49)
    private String deadlineDate;

    @ApiModelProperty(position = 50)
    private String chatId;

}
