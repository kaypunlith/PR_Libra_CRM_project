package com.ut.nlSystemAPi.model.entity.CreditMemo;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreditMemoServices extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long creditMemoId;

    @ApiModelProperty(position = 3)
    private Long discountId;

    @ApiModelProperty(position = 4)
    private Double discountAmount;

    @ApiModelProperty(position = 5)
    private Double discountPercent;

    @ApiModelProperty(position = 6)
    private Long productId;

    @ApiModelProperty(position = 6)
    private Long serviceId;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long qtyFree;

    @ApiModelProperty(position = 9)
    private Long uomId;

    @ApiModelProperty(position = 10)
    private Double unitPrice;

    @ApiModelProperty(position = 11)
    private Double totalPrice;

    @ApiModelProperty(position = 12)
    private Long lotsNumber;

    @ApiModelProperty(position = 12)
    private String expiredDate;

    @ApiModelProperty(position = 13)
    private Long conversion;

    @ApiModelProperty(position = 14)
    private String note;

    @ApiModelProperty(position = 14)
    private String description;

}
