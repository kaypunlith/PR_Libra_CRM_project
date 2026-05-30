package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerSummaryResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String organizationCode;

    @ApiModelProperty(position = 3)
    private String organizationName;

    @ApiModelProperty(position = 1)
    private List<CustomerSummaryTransactionResponse> transactions;

}
