package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryNoteReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String dnCode;

    @ApiModelProperty(position = 3)
    private String dnDate;

    @ApiModelProperty(position = 4)
    private String organizationGroupName;

    @ApiModelProperty(position = 5)
    private String organizationName;

    @ApiModelProperty(position = 6)
    private String invoiceCode;

    @ApiModelProperty(position = 7)
    private String created;

    @ApiModelProperty(position = 8)
    private Integer status;
}