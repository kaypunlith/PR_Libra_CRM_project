package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GraphDashboardResponse {

    @ApiModelProperty(position = 1)
    private Long month;

    @ApiModelProperty(position = 2)
    private Double smc;

    @ApiModelProperty(position = 3)
    private Double omRon;

    @ApiModelProperty(position = 4)
    private Double general;
}
