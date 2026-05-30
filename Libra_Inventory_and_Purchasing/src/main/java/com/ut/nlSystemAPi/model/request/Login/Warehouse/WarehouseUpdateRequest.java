package com.ut.nlSystemAPi.model.request.Login.Warehouse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WarehouseUpdateRequest extends WarehouseRequest{

    @ApiModelProperty(position = 1)
    private Long id;

}
