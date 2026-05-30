package com.ut.nlSystemAPi.model.response.Report.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StatementReportDetailResponse {

    @ApiModelProperty(position = 1)
    private String label;

    @ApiModelProperty(position = 2)
    private Double balance;

}