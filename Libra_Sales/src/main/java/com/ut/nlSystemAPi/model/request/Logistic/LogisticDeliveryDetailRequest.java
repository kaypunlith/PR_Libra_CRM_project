package com.ut.nlSystemAPi.model.request.Logistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class LogisticDeliveryDetailRequest {

    @ApiModelProperty(position = 1)
    private String type;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 3)
    private Long conversion;

    @ApiModelProperty(position = 4)
    private Long qty;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private String deliveryDate;


}