package com.ut.nlSystemAPi.model.response.Report.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StatementReportResponse {

    @ApiModelProperty(position = 1)
    private Long organizationId;

    @ApiModelProperty(position = 2)
    private String organizationName;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String transaction;

    @ApiModelProperty(position = 6)
    private Double amount;

    @ApiModelProperty(position = 6)
    private Double balance;

    @ApiModelProperty(position = 13)
    private List<StatementReportDetailResponse> details;

}