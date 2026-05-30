package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

@Data
public class PayrollRequest implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 7)
    private Long departmentId;

    @ApiModelProperty(position = 7)
    private Long positionId;

    @ApiModelProperty(position = 10)
    private Float currentSalary;

    @ApiModelProperty(position = 11)
    private Float increaseSalary;

    @ApiModelProperty(position = 12)
    private Long paidType;

    @ApiModelProperty(position = 13)
    private String accountNumber;

    @ApiModelProperty(position = 14)
    private String accountId;

    @ApiModelProperty(position = 15)
    private String payDate;

    @ApiModelProperty(position = 16)
    private List<PayrollDetailRequest> details;

    @ApiModelProperty(position = 17)
    private List<InsertOtherPayRequest> insertOtherPay;

    @ApiModelProperty(position = 18)
    private Float totalAmount;

    @ApiModelProperty(position = 19)
    private String note;
}
