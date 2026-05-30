package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseOrderDetailDropdownResponse {

    @ApiModelProperty(position = 1)
    private Integer type;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long itemId;

    @ApiModelProperty(position = 4)
    private String itemName;

    @ApiModelProperty(position = 5)
    private String itemCode;

    @ApiModelProperty(position = 6)
    private String sku;

    @ApiModelProperty(position = 7)
    private String upc;

    @ApiModelProperty(position = 8)
    private Double qty;

    @ApiModelProperty(position = 9)
    private Double conversion;

    @ApiModelProperty(position = 10)
    private Long uomId;

    @ApiModelProperty(position = 11)
    private String uomName;

    @ApiModelProperty(position = 12)
    private String uomAbbr;

    @ApiModelProperty(position = 13)
    private Double unitCost;

    @ApiModelProperty(position = 14)
    private Double totalCost;

    @ApiModelProperty(position = 15)
    private String note;
}
