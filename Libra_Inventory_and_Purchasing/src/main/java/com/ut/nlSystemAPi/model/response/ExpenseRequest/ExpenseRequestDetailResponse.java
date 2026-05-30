package com.ut.nlSystemAPi.model.response.ExpenseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExpenseRequestDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 1)
    private Long itemId;

    @ApiModelProperty(position = 1)
    private String itemName;

    @ApiModelProperty(position = 2)
    private String itemCode;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private Long conversion;

    @ApiModelProperty(position = 4)
    private Long qty;

    @ApiModelProperty(position = 5)
    private String uom;

    @ApiModelProperty(position = 6)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private String note;

    @ApiModelProperty(position = 7)
    private Double unitCost;

    @ApiModelProperty(position = 8)
    private Double unitPrice;

    @ApiModelProperty(position = 9)
    private String marginal;

    @ApiModelProperty(position = 10)
    private Double totalCost;

    @ApiModelProperty(position = 11)
    private Double totalPrice;

    @ApiModelProperty(position = 12)
    private Double totalMarginal;

}
