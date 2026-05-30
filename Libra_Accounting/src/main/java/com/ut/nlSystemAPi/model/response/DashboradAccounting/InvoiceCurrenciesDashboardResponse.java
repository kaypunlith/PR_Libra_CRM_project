package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InvoiceCurrenciesDashboardResponse {

    @ApiModelProperty(position = 3)
    private Long id;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 3)
    private Double totalInvoiceCurrencies;
}
