package com.ut.nlSystemAPi.model.response.Warehouse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserWarehouseResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;



}
