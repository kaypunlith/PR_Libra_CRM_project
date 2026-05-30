package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChartAccountResponse {

    @ApiModelProperty(position = 1)
    private Long chartAccountId;

    @ApiModelProperty(position = 2)
    private String chartAccountName;

    @ApiModelProperty(position = 2)
    private Double totalAmount;

    @ApiModelProperty(position = 1)
    private List<ChartAccountSubDetail> chartAccountSubDetails;
}
