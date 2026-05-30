package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TotalSalesReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private String organizationName;

    @ApiModelProperty(position = 5)
    private String viewBy;

    @ApiModelProperty(position = 6)
    private Double totalAmount;

    @ApiModelProperty(position = 7)
    private Double balance;

    @ApiModelProperty(position = 8)
    private String aging;

    @ApiModelProperty(position = 9)
    private Integer isClose;

    @ApiModelProperty(position = 10)
    private Integer status;

    @ApiModelProperty(position = 11)
    private String currencySymbol;

}