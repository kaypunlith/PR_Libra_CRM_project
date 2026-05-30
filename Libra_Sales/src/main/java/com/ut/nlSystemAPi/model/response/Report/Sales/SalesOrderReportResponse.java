package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SalesOrderReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String soDate;

    @ApiModelProperty(position = 3)
    private String soCode;

    @ApiModelProperty(position = 4)
    private String organizationName;

    @ApiModelProperty(position = 5)
    private String quotationCode;

    @ApiModelProperty(position = 5)
    private String dnCode;

    @ApiModelProperty(position = 5)
    private String poCode;

    @ApiModelProperty(position = 6)
    private Double subTotal;

    @ApiModelProperty(position = 6)
    private Double totalAmount;

    @ApiModelProperty(position = 7)
    private Double totalVat;

    @ApiModelProperty(position = 8)
    private String aging;

    @ApiModelProperty(position = 9)
    private Integer isClose;

    @ApiModelProperty(position = 10)
    private Integer isApprove;

    @ApiModelProperty(position = 11)
    private Integer type;

    @ApiModelProperty(position = 2)
    private String itemName;

    @ApiModelProperty(position = 8)
    private Long qty;

    @ApiModelProperty(position = 9)
    private String uomName;

    @ApiModelProperty(position = 9)
    private String sku;

    @ApiModelProperty(position = 9)
    private String upc;

    @ApiModelProperty(position = 10)
    private Double unitCost;

    @ApiModelProperty(position = 11)
    private Double totalCost;

    @ApiModelProperty(position = 12)
    private String createdBy;

    @ApiModelProperty(position = 13)
    private String currencySymbol;

    @ApiModelProperty(position = 5)
    private List<SalesOrderReportDetailResponse> details;

}