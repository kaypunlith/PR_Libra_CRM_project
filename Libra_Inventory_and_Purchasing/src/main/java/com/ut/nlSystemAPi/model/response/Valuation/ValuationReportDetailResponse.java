package com.ut.nlSystemAPi.model.response.Valuation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ValuationReportDetailResponse {

    @ApiModelProperty(position = 3)
    private String uom;

    @ApiModelProperty(position = 4)
    private Double onHand;

    @ApiModelProperty(position = 5)
    private Double qty;

    @ApiModelProperty(position = 6)
    private Double smallQty;

    @ApiModelProperty(position = 7)
    private Double cost;

    @ApiModelProperty(position = 8)
    private Double price;

    @ApiModelProperty(position = 9)
    private Double onHandSmall;

    @ApiModelProperty(position = 10)
    private Double avgCost;

    @ApiModelProperty(position = 11)
    private Double assetValue;

    @ApiModelProperty(position = 12)
    private Long isVarCost;

    @ApiModelProperty(position = 13)
    private Long isAdjustValue;

    @ApiModelProperty(position = 14)
    private String type;

    @ApiModelProperty(position = 15)
    private String date;

    @ApiModelProperty(position = 16)
    private String reference;

    @ApiModelProperty(position = 17)
    private String invoiceCode;

    @ApiModelProperty(position = 18)
    private String pbCode;
}
