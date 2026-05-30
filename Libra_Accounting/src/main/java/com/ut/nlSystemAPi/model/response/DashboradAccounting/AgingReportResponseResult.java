package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AgingReportResponseResult {

    @ApiModelProperty(position = 1)
    private Double outstandingAmount;

    @ApiModelProperty(position = 1)
    private Double overdueAmount;

}
