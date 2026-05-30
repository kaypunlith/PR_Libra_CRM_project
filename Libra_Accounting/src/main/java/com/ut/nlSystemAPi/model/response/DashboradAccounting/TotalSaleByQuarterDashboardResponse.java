package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TotalSaleByQuarterDashboardResponse {
    
    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private String year;

    @ApiModelProperty(position = 3)
    private String monthFrom;

    @ApiModelProperty(position = 4)
    private String monthTo;

    @ApiModelProperty(position = 4)
    private Double grandTotalAmount;

    @ApiModelProperty(position = 12)
    private List<TotalSaleByQuarterDetailDashboardResponse> detailResponses;
}
