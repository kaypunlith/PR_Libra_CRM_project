package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data

public class ProfitAndLostReport {
    @ApiModelProperty(position = 1)
    private  String title;

    @ApiModelProperty(position = 2)
    private Double totalAmount;

    @ApiModelProperty(position = 5)
    private List<accountGroupDetail> accountGroupDetals;

    @ApiModelProperty(position = 3)
    private List<ChartAccountSubDetail> totalByColumn;

}
