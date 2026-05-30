package com.ut.nlSystemAPi.model.response.StockLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockLevelResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long priceTypeId;

    @ApiModelProperty(position = 4)
    private String priceTypeName;

    @ApiModelProperty(position = 5)
    private String created;

    @ApiModelProperty(position = 6)
    private String createdBy;

    @ApiModelProperty(position = 7)
    private String modified;

    @ApiModelProperty(position = 8)
    private String modifiedBy;

    @ApiModelProperty(position = 9)
    private Integer isActive;
}
