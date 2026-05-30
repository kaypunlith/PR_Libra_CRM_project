package com.ut.nlSystemAPi.model.request.Login.Shipment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShipmentUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;
}
