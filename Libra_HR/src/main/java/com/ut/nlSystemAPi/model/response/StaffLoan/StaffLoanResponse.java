package com.ut.nlSystemAPi.model.response.StaffLoan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaffLoanResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private Long positionId;

    @ApiModelProperty(position = 3)
    private Long gender;

    @ApiModelProperty(position = 5)
    private String departmentName;

    @ApiModelProperty(position = 5)
    private String employeeName;

    @ApiModelProperty(position = 5)
    private String positionName;

    @ApiModelProperty(position = 4)
    private Integer paybackPeriod;

    @ApiModelProperty(position = 5)
    private String paybackDate;

    @ApiModelProperty(position = 6)
    private Double amount;

    @ApiModelProperty(position = 8)
    private String created;

    @ApiModelProperty(position = 9)
    private String modified;

    @ApiModelProperty(position = 10)
    private String createdBy;

    @ApiModelProperty(position = 11)
    private String modifiedBy;

    @ApiModelProperty(position = 12)
    private Integer status;
}
