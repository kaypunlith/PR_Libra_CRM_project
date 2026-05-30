package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollDetailRequest implements Serializable {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2, hidden = true)
    private Long payrollId;

    @ApiModelProperty(position = 3)
    private Long payrollItemId;

    @ApiModelProperty(position = 4)
    private Float amount;

    @ApiModelProperty(position = 4)
    private Long status;

}
