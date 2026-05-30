package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class ChartAccountSubDetail {

    @ApiModelProperty(position = 1)
    private String date;

    @ApiModelProperty(position = 1)
    private String className;

    @ApiModelProperty(position = 2)
    private Double totalAmount;

    @ApiModelProperty(position = 2)
    private Double debit;

    @ApiModelProperty(position = 2)
    private Double credit;

    @ApiModelProperty(position = 2)
    private Double amountAll;

}
