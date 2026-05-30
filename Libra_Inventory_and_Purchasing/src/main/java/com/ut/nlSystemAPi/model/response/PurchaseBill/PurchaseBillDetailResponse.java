package com.ut.nlSystemAPi.model.response.PurchaseBill;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseBillDetailResponse extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 2)
    private String itemName;

    @ApiModelProperty(position = 2)
    private String upc;

    @ApiModelProperty(position = 2)
    private String sku;

    @ApiModelProperty(position = 3)
    private String note;

    @ApiModelProperty(position = 3)
    private Long isExpiredDate;

    @ApiModelProperty(position = 3)
    private String expiredDate;

    @ApiModelProperty(position = 4)
    private Long qty;

    @ApiModelProperty(position = 4)
    private Long foc;

    @ApiModelProperty(position = 5)
    private Long conversion;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private String uomName;

    @ApiModelProperty(position = 6)
    private Double totalCost;

    @ApiModelProperty(position = 7)
    private Double discountAmount;

    @ApiModelProperty(position = 7)
    private Double discountPercent;

    @ApiModelProperty(position = 7)
    private Double unitCost;

}
