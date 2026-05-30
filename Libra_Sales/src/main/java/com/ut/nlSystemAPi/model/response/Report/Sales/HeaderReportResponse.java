package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class HeaderReportResponse {

    @ApiModelProperty(position = 1)
    private String header;

    @ApiModelProperty(position = 2)
    private List<SalesByItemReportResponse> main;

}