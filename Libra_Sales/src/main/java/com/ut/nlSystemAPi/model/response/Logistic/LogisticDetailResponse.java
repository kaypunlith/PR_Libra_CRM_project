package com.ut.nlSystemAPi.model.response.Logistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class LogisticDetailResponse {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long itemId;

    @ApiModelProperty(position = 4)
    private String itemName;

    @ApiModelProperty(position = 5)
    private String sku;

    @ApiModelProperty(position = 6)
    private String upc;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long conversion;

    @ApiModelProperty(position = 9)
    private Long uomId;

    @ApiModelProperty(position = 10)
    private String uomName;

    @ApiModelProperty(position = 11)
    private String uomAbbr;

    @ApiModelProperty(position = 12)
    private Double unitCost;

    @ApiModelProperty(position = 13)
    private Double totalCost;

    @ApiModelProperty(position = 11)
    private String status;

    @ApiModelProperty(position = 12)
    private String deliveryDate;

    @ApiModelProperty(position = 13)
    private String createdBy;

    @ApiModelProperty(position = 14)
    private String created;

}
