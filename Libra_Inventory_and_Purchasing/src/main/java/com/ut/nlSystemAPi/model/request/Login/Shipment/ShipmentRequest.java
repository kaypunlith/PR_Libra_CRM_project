package com.ut.nlSystemAPi.model.request.Login.Shipment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class ShipmentRequest {
    @ApiModelProperty(position = 1)
    private String name;
}
