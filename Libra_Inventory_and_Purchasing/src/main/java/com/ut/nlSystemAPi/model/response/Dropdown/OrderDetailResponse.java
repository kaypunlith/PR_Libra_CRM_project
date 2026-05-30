package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long type;

    @ApiModelProperty(position = 5)
    private Long itemId;

    @ApiModelProperty(position = 6)
    private String itemName;

    @ApiModelProperty(position = 6)
    private String upc;

    @ApiModelProperty(position = 6)
    private String sku;

    @ApiModelProperty(position = 8)
    private Long categoryId;

    @ApiModelProperty(position = 9)
    private String categoryName;

    @ApiModelProperty(position = 10)
    private Long orderId;

    @ApiModelProperty(position = 11)
    private Long qty;

    @ApiModelProperty(position = 11)
    private Long uomId;

    @ApiModelProperty(position = 11)
    private String uomName;

    @ApiModelProperty(position = 12)
    private Double conversion;

    @ApiModelProperty(position = 12)
    private Double unitPrice;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

}
