package com.ut.nlSystemAPi.model.response.LandedCost;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LandedCostDetailResponse extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long landedCostId;

    @ApiModelProperty(position = 3)
    private Long purchaseBillId;

    @ApiModelProperty(position = 3)
    private Long purchaseBillDetailId;

    @ApiModelProperty(position = 3)
    private Long vendorId;

    @ApiModelProperty(position = 3)
    private String vendorName;

    @ApiModelProperty(position = 4)
    private Long itemId;

    @ApiModelProperty(position = 4)
    private String itemName;

    @ApiModelProperty(position = 5)
    private String sku;

    @ApiModelProperty(position = 6)
    private String upc;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 8)
    private String uomName;

    @ApiModelProperty(position = 9)
    private Long conversion;

    @ApiModelProperty(position = 10)
    private Long smallValUom;

    @ApiModelProperty(position = 11)
    private Double landedCost;

    @ApiModelProperty(position = 12)
    private Double unitCost;

}
