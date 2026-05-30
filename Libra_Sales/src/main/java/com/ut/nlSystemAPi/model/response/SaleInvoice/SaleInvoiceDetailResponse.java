package com.ut.nlSystemAPi.model.response.SaleInvoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleInvoiceDetailResponse {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long itemId;

    @ApiModelProperty(position = 4)
    private String itemName;

    @ApiModelProperty(position = 4)
    private String itemBrand;

    @ApiModelProperty(position = 4)
    private String poNo;

    @ApiModelProperty(position = 5)
    private String sku;

    @ApiModelProperty(position = 6)
    private String upc;

    @ApiModelProperty(position = 6)
    private String spec;

    @ApiModelProperty(position = 6)
    private String note;

    @ApiModelProperty(position = 7)
    private Long qtyInStock;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long qtyFree;

    @ApiModelProperty(position = 9)
    private Double conversion;

    @ApiModelProperty(position = 10)
    private Long uomId;

    @ApiModelProperty(position = 11)
    private String uomName;

    @ApiModelProperty(position = 12)
    private String uomAbbr;

    @ApiModelProperty(position = 13)
    private Integer isExpiredDate;

    @ApiModelProperty(position = 14)
    private Long month;

    @ApiModelProperty(position = 14)
    private String startDate;

    @ApiModelProperty(position = 14)
    private Long discountId;

    @ApiModelProperty(position = 15)
    private Double discountAmount;

    @ApiModelProperty(position = 16)
    private Double discountPercent;

    @ApiModelProperty(position = 17)
    private Double unitPrice;

    @ApiModelProperty(position = 18)
    private Double totalPrice;

    @ApiModelProperty(position = 19)
    private Long depositTo;

    @ApiModelProperty(position = 20)
    private String depositToName;

}