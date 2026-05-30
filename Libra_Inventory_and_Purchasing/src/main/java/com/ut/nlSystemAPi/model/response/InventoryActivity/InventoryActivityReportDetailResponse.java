package com.ut.nlSystemAPi.model.response.InventoryActivity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class InventoryActivityReportDetailResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 2)
    private String uomName;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 2)
    private String reference;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 3)
    private Double qty;

    @ApiModelProperty(position = 7)
    private Double subTotalQty;

    @ApiModelProperty(position = 3)
    private String warehouseName;

    @ApiModelProperty(position = 3)
    private String lotNo;

    @ApiModelProperty(position = 3)
    private String expiredDate;

    @ApiModelProperty(position = 3)
    private Long locationId;

    @ApiModelProperty(position = 3)
    private String locationName;

    @ApiModelProperty(position = 3)
    private String createdBy;

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 6)
    private Long productGroupId;

    @ApiModelProperty(position = 6)
    private String productGroupName;

    @ApiModelProperty(position = 6)
    private String note;

    @ApiModelProperty(position = 7)
    private Long status;

    @ApiModelProperty(position = 7)
    private Double beginning;

    @ApiModelProperty(position = 7)
    private Double inOutAmount;

    @ApiModelProperty(position = 7)
    private Double adjustment;

    @ApiModelProperty(position = 7)
    private Double po;

    @ApiModelProperty(position = 7)
    private Double br;

    @ApiModelProperty(position = 7)
    private Double sale;

    @ApiModelProperty(position = 7)
    private Double cm;

    @ApiModelProperty(position = 7)
    private Double transfer;

    @ApiModelProperty(position = 7)
    private Double ending;

    @ApiModelProperty(position = 7)
    private Double totalOrder;

    @ApiModelProperty(position = 7)
    private Double unitCost;

    @ApiModelProperty(position = 7)
    private Double amount;



}
