package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TotalSaleByQuarterDetailDashboardResponse {

    @ApiModelProperty(position = 1)
    private Long month;

    @ApiModelProperty(position = 2)
    private Double totalAmount;

}
