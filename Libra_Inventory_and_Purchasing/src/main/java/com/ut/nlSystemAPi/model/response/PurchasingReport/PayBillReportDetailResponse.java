package com.ut.nlSystemAPi.model.response.PurchasingReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PayBillReportDetailResponse {

    @ApiModelProperty(position = 3)
    private String type;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String companyName;

    @ApiModelProperty(position = 6)
    private String reference;

    @ApiModelProperty(position = 7)
    private String chartAccountName;

    @ApiModelProperty(position = 8)
    private Double amount;

}
