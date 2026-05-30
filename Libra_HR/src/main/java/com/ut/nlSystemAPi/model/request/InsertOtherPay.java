package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InsertOtherPay extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long payrollId;

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 1)
    private Long branchId;

    @ApiModelProperty(position = 1)
    private Long bonusId;

    @ApiModelProperty(position = 1)
    private Float amount;
}
