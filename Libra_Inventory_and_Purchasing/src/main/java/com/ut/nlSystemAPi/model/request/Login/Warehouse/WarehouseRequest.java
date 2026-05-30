package com.ut.nlSystemAPi.model.request.Login.Warehouse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WarehouseRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private String description;

    @ApiModelProperty(position = 3)
    private List<Long> users;

    @ApiModelProperty(position = 4)
    private Long classId;

    @ApiModelProperty(position = 5)
    private Long stockLevelId;

}
