package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollDetailStatusResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long payrollId;

    @ApiModelProperty(position = 2)
    private Long payrollItemId;

    @ApiModelProperty(position = 3)
    private Float amount;

    @ApiModelProperty(position = 4)
    private Long status;
}
