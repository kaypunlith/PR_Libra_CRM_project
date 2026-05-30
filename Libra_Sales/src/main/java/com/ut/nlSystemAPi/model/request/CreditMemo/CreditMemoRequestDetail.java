package com.ut.nlSystemAPi.model.request.CreditMemo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.DateUtil;

@Data
public class CreditMemoRequestDetail {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long discountId;

    @ApiModelProperty(position = 3)
    private Double discountAmount;

    @ApiModelProperty(position = 4)
    private Double discountPercent;

    @ApiModelProperty(position = 5)
    private Long itemId;

    @ApiModelProperty(position = 5)
    private String itemName;

    @ApiModelProperty(position = 6)
    private Long qty;

    @ApiModelProperty(position = 7)
    private Long qtyFree;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 9)
    private Double unitPrice;

    @ApiModelProperty(position = 10)
    private Double totalPrice;

    @ApiModelProperty(position = 11)
    private Long lotsNumber;

    @ApiModelProperty(position = 12)
    private String expiredDate;

    @ApiModelProperty(position = 13)
    private Long conversion;

    @ApiModelProperty(position = 14)
    private String note;

}
