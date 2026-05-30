package com.ut.nlSystemAPi.model.entity.Delivery;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Delivery extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 5)
    private String note;

    @ApiModelProperty(position = 6)
    private Long warehouseId;

    @ApiModelProperty(position = 7)
    private Long customerGroupId;

    @ApiModelProperty(position = 8)
    private Long customerId;

    @ApiModelProperty(position = 8)
    private Long deliveryId;

    @ApiModelProperty(position = 9)
    private Integer status;
}
