package com.ut.nlSystemAPi.model.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartAccountNetIncomeFilter {

    @ApiModelProperty(position = 10)
    private Long chartAccountId;

    @ApiModelProperty(position = 10)
    private String date;

    @ApiModelProperty(position = 10)
    private Long companyId;

    @ApiModelProperty(position = 10)
    private Long branchId;


}
