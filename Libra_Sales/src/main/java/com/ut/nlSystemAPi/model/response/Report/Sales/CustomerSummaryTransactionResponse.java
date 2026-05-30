package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerSummaryTransactionResponse {

    @ApiModelProperty(position = 1)
    private String year;

    @ApiModelProperty(position = 1)
    private String type;

    @ApiModelProperty(position = 4)
    private Long count;

    @ApiModelProperty(position = 5)
    private Double amount;

}
