package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollBankPaid implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String idCard;

    @ApiModelProperty(position = 3)
    private String fullName;

    @ApiModelProperty(position = 4)
    private String accountId;

    @ApiModelProperty(position = 5)
    private String accountNumber;

    @ApiModelProperty(position = 6)
    private String sex;

    @ApiModelProperty(position = 7)
    private Float totalSalary;

    @ApiModelProperty(position = 8)
    private String remarks;

}
