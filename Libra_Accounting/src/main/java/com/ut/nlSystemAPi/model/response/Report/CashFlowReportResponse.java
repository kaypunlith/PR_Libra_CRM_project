package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CashFlowReportResponse {
    @ApiModelProperty(position = 1)
    private String title;

    @ApiModelProperty(position = 2)
    private Double totalAmount;

    @ApiModelProperty(position = 3)
    private Double netIncome;

    @ApiModelProperty(position = 3)
    List<CashFlowGroupResponse> cashFlowGroupResponses;

    @ApiModelProperty(position = 1)
    private List<ChartAccountSubDetail> netIncomeByMonth;


}
