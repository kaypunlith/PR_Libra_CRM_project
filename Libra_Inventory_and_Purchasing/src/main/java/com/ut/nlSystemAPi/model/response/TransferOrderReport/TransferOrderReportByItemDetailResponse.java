package com.ut.nlSystemAPi.model.response.TransferOrderReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TransferOrderReportByItemDetailResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String toNumber;

    @ApiModelProperty(position = 6)
    private String toWarehouseName;

    @ApiModelProperty(position = 7)
    private String fromWarehouseName;

    @ApiModelProperty(position = 8)
    private Long qty;

    @ApiModelProperty(position = 9)
    private String uom;

    @ApiModelProperty(position = 10)
    private Double unitCost;

    @ApiModelProperty(position = 10)
    private Double conversion;

    @ApiModelProperty(position = 10)
    private Double totalCost;

    @ApiModelProperty(position = 10)
    private Double totalCostForGrand;

    @ApiModelProperty(position = 11)
    private Double subTotalQty;

    @ApiModelProperty(position = 11)
    private Double grandDetailTotalCost;

    @ApiModelProperty(position = 12)
    private Double grandDetailTotalUnitCost;

}
