package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollBeginning extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeesId;

    @ApiModelProperty(position = 3)
    private Long positionId;

    @ApiModelProperty(position = 4)
    private Float currentSalary;

    @ApiModelProperty(position = 5)
    private Float increaseSalary;

    @ApiModelProperty(position = 6)
    private Long paidType;

    @ApiModelProperty(position = 7)
    private String accountNumber;

    @ApiModelProperty(position = 8)
    private String accountId;

    @ApiModelProperty(position = 9)
    private Float totalAmount;

    @ApiModelProperty(position = 10)
    private String payDate;

    @ApiModelProperty(position = 11)
    private String note;
}
