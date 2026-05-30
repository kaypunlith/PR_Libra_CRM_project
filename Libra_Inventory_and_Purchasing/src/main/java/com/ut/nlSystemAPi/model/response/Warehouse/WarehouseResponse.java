package com.ut.nlSystemAPi.model.response.Warehouse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WarehouseResponse {

    @ApiModelProperty(position = 1)
    private Long warehouseId;

    @ApiModelProperty(position = 2)
    private String warehouseName;

    @ApiModelProperty(position = 2)
    private Long stockLevelId;

    @ApiModelProperty(position = 2)
    private String stockLevelName;

    @ApiModelProperty(position = 2)
    private Long classId;

    @ApiModelProperty(position = 2)
    private String className;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private String created;

    @ApiModelProperty(position = 5)
    private String createdBy;

    @ApiModelProperty(position = 6)
    private String modified;

    @ApiModelProperty(position = 7)
    private String modifiedBy;

    @ApiModelProperty(position = 8)
    private List<LocationWarehouseResponse> locations;

    @ApiModelProperty(position = 8)
    private List<UserWarehouseResponse> users;

}
