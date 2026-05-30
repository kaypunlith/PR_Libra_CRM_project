package com.ut.nlSystemAPi.model.response.CreditMemo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreditMemoDetailResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 3)
    private Long itemId;

    @ApiModelProperty(position = 4)
    private String itemName;

    @ApiModelProperty(position = 5)
    private String sku;

    @ApiModelProperty(position = 6)
    private String upc;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 7)
    private Long qtyFree;

    @ApiModelProperty(position = 8)
    private Long conversion;

    @ApiModelProperty(position = 9)
    private Long uomId;

    @ApiModelProperty(position = 10)
    private String uomName;

    @ApiModelProperty(position = 11)
    private String uomAbbr;

    @ApiModelProperty(position = 12)
    private Double unitPrice;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

    @ApiModelProperty(position = 14)
    private Long discountId;

    @ApiModelProperty(position = 15)
    private Double discountAmount;

    @ApiModelProperty(position = 16)
    private Double discountPercent;

    @ApiModelProperty(position = 17)
    private String expiredDate;

    @ApiModelProperty(position = 18)
    private String note;

    @ApiModelProperty(position = 19)
    private Integer isExpiredDate;
}
