package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GraphicResponseResult {
    @ApiModelProperty(position = 1)
    private Long netDay;

    @ApiModelProperty(position = 1)
    private Double outstandingAmount;

    @ApiModelProperty(position = 1)
    private Double overdueAmount;

    @ApiModelProperty(position = 5)
    private Double grandOutstandingAmount;

    @ApiModelProperty(position = 6)
    private Double grandOverdueAmount;

    @ApiModelProperty(position = 6)
    private Double grandTotalAmount;

}
