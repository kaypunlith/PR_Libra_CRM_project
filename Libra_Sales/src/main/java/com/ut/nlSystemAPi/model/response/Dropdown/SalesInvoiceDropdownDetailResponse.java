package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesInvoiceDropdownDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 3)
    private Long boqId;

    @ApiModelProperty(position = 4)
    private String itemName;

    @ApiModelProperty(position = 5)
    private Long productId;

    @ApiModelProperty(position = 6)
    private String sku;

    @ApiModelProperty(position = 7)
    private String upc;

    @ApiModelProperty(position = 8)
    private String productName;

    @ApiModelProperty(position = 9)
    private Boolean isExpiredDate;

    @ApiModelProperty(position = 10)
    private Double conversion;

    @ApiModelProperty(position = 11)
    private Double qty;

    @ApiModelProperty(position = 12)
    private Long uomId;

    @ApiModelProperty(position = 13)
    private String uomName;

    @ApiModelProperty(position = 14)
    private Double unitPrice;

    @ApiModelProperty(position = 15)
    private Double totalPrice;

    @ApiModelProperty(position = 16)
    private String note;

}
