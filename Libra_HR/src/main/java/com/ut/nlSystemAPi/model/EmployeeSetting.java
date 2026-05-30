package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.response.EmployeeDocumentResponse;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeSetting extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String profilePhoto;

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
    private Long paidType;

    @ApiModelProperty(position = 23)
    private String accountNumber;

    @ApiModelProperty(position = 24)
    private String accountId;

    @ApiModelProperty(position = 25)
    private Long donate;

    @ApiModelProperty(position = 25)
    private Long providenceFund;

    // Status 1 , 0 Frontend need to show in string
    @ApiModelProperty(position = 26)
    private Long deposit;

    @ApiModelProperty(position = 27)
    private Float cashAmount;

    @ApiModelProperty(position = 28)
    private Long numOfMonth;

    @ApiModelProperty(position = 26)
    private Float beginningDeposit;

    @ApiModelProperty(position = 26)
    private String beginningDepositDate;

    @ApiModelProperty(position = 28)
    private Long beginningDepositMonth;

    @ApiModelProperty(position = 26)
    private Float beginningProFund;

    @ApiModelProperty(position = 28)
    private Long beginningProFundMonth;

    @ApiModelProperty(position = 29)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long employeeStatusId;

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
    private Long payroll;

    @ApiModelProperty(position = 34)
    private List<EmployeeDocumentResponse> employeeDocuments;

}
