package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ARDetailReportResponse {

    @ApiModelProperty(position = 1)
    private Long from;

    @ApiModelProperty(position = 2)
    private Long to;

    @ApiModelProperty(position = 3)
    private List<ARListDetailReportResponse> arListDetailReportResponses;

    @ApiModelProperty(position = 2)
    private Double grandTotalBalance;

}
