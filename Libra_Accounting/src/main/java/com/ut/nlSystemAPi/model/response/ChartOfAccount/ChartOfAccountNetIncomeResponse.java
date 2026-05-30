package com.ut.nlSystemAPi.model.response.ChartOfAccount;

import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChartOfAccountNetIncomeResponse {

    @ApiModelProperty(position = 1)
    private Double retainedEarningDebit;

    @ApiModelProperty(position = 2)
    private Double retainedEarningCredit;

    @ApiModelProperty(position = 3)
    private Double incomeSummaryDebit;

    @ApiModelProperty(position = 4)
    private Double incomeSummaryCredit;

}
