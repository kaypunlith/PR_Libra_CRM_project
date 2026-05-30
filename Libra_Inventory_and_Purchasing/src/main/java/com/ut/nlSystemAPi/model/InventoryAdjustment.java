package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InventoryAdjustment extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String reference;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private Long warehouseId;

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 6)
    private Long depositTo;

    @ApiModelProperty(position = 7)
    private Long productGroupId;

    @ApiModelProperty(position = 8)
    private String note;

    @ApiModelProperty(position = 9)
    private Long cpType;

    @ApiModelProperty(position = 10)
    private Long type;

}
