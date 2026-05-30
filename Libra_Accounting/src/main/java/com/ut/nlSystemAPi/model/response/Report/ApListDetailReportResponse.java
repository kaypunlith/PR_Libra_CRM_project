package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApListDetailReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Double amount;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String dueDate;

    @ApiModelProperty(position = 5)
    private String vendorId;

    @ApiModelProperty(position = 6)
    private String vendorName;

    @ApiModelProperty(position = 6)
    private String branchName;

    @ApiModelProperty(position = 6)
    private Long branchId;

    @ApiModelProperty(position = 7)
    private String invoiceNo;

    @ApiModelProperty(position = 8)
    private String note;

    @ApiModelProperty(position = 8)
    private String terms;

    @ApiModelProperty(position = 9)
    private String duDate;

    @ApiModelProperty(position = 10)
    private Long netDays;

    @ApiModelProperty(position = 10)
    private String aging;

    @ApiModelProperty(position = 11)
    private Double openBalance;

    @ApiModelProperty(position = 11)
    private String type;

    @ApiModelProperty(position = 11)
    private Double grandTotal;
}
