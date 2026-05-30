package com.ut.nlSystemAPi.model.request.BOM;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BOMDetailRequest {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 2)
    private String itemName;

    @ApiModelProperty(position = 4)
    private Long qty;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private Long conversion;

    @ApiModelProperty(position = 6)
    private Double totalCost;

    @ApiModelProperty(position = 7)
    private Double unitCost;

    @ApiModelProperty(position = 11)
    private String remark;

    @ApiModelProperty(position = 12)
    private Double unitPrice;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

}
