package com.ut.nlSystemAPi.model.response.DeliveryNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryNoteProductStockResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String lotsNumber;

    @ApiModelProperty(position = 3)
    private String expiredDate;

    @ApiModelProperty(position = 5)
    private String locationName;

    @ApiModelProperty(position = 6)
    private Double totalQty;

    @ApiModelProperty(position = 7)
    private Long locationId;

    @ApiModelProperty(position = 7)
    private String uomName;
}

