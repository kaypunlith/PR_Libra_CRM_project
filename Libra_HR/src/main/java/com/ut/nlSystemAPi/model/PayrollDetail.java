package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollDetail extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long payrollId;

    @ApiModelProperty(position = 3)
    private Long payrollItemId;

    @ApiModelProperty(position = 5)
    private Float amount;

    @ApiModelProperty(position = 6)
    private Long payrollStatus;

}
