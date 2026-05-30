package com.ut.nlSystemAPi.model.entity.SaleInvoice;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SaleInvoiceServices extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long saleInvoiceId;

    @ApiModelProperty(position = 2)
    private Long quotationId;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 2)
    private String poNo;

    @ApiModelProperty(position = 4)
    private Long qty;

    @ApiModelProperty(position = 4)
    private Long qtyFree;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private Long conversion;

    @ApiModelProperty(position = 5)
    private Long month;

    @ApiModelProperty(position = 5)
    private String startDate;

    @ApiModelProperty(position = 7)
    private Long discountId;

    @ApiModelProperty(position = 7)
    private Double discountAmount;

    @ApiModelProperty(position = 7)
    private Double discountPercent;

    @ApiModelProperty(position = 12)
    private Double unitPrice;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

    @ApiModelProperty(position = 13)
    private String note;
}
