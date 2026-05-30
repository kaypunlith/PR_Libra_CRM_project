package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProvidentFundRequestDetail {

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private float amountRequest;

    @ApiModelProperty(position = 3)
    private Long status;

    @ApiModelProperty(position = 4)
    private Long totalOfMonth;

    @ApiModelProperty(position = 5)
    private Float bonusHaftProfundAmount;

}
