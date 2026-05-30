package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InventorySample {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String reference;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private Long warehouseId;

    @ApiModelProperty(position = 6)
    private Long pgroupId;

    @ApiModelProperty(position = 7)
    private Long inventorySampleProductId;

    @ApiModelProperty(position = 8)
    private Long productId;

    @ApiModelProperty(position = 9)
    private Long locationId;

    @ApiModelProperty(position = 10)
    private String lotsNumber;

    @ApiModelProperty(position = 11)
    private String expiredDate;

    @ApiModelProperty(position = 12)
    private Long qtyOnHand;

    @ApiModelProperty(position = 13)
    private Long newQty;

    @ApiModelProperty(position = 14)
    private Long adjustQty;

    @ApiModelProperty(position = 15)
    private Long modifiedBy;

    @ApiModelProperty(position = 15)
    private String modified;

    @ApiModelProperty(position = 16)
    private Long createdBy;

    @ApiModelProperty(position = 17)
    private Long status;



}
