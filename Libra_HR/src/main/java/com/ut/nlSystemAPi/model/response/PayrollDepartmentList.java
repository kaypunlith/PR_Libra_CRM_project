package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PayrollDepartmentList implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private List<PayrollBankPaid> bankPaidList;

    @ApiModelProperty(position = 3)
    private List<PayrollEmployeesList> employeesList;
}
