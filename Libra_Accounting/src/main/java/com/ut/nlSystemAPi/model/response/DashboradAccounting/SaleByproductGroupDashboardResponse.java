package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleByproductGroupDashboardResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 5)
    private Double totalAmount;

    @ApiModelProperty(position = 6)
    private Double totalInvoice;


}
