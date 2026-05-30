package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaffLoan extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private Long positionId;

    @ApiModelProperty(position = 4)
    private Long paybackPeriod;

    @ApiModelProperty(position = 5)
    private String paybackDate;

    @ApiModelProperty(position = 6)
    private Double amount;

    @ApiModelProperty(position = 7)
    private Integer isApprove;
}
