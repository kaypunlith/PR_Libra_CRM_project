package com.ut.nlSystemAPi.model.response.PurchasingReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseByItemResponse {

    @ApiModelProperty(position = 1)
    private Long itemId;

    @ApiModelProperty(position = 2)
    private String itemName;

    @ApiModelProperty(position = 1)
    private Long parentId;

    @ApiModelProperty(position = 2)
    private String parentName;

    @ApiModelProperty(position = 2)
    private Double qty;

    @ApiModelProperty(position = 2)
    private String uomName;

    @ApiModelProperty(position = 3)
    private Double totalCost;

    @ApiModelProperty(position = 4)
    private Double totalQty;

    @ApiModelProperty(position = 4)
    private Double subTotal;

    @ApiModelProperty(position = 6)
    private List<PurchaseByItemDetailResponse> details;


}
