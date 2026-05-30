package com.ut.nlSystemAPi.model.response.PurchasingReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseByItemDetailResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 2)
    private String vendorName;

    @ApiModelProperty(position = 2)
    private String locationName;

    @ApiModelProperty(position = 2)
    private Long qty;

    @ApiModelProperty(position = 2)
    private String uomName;

    @ApiModelProperty(position = 2)
    private Double unitCost;

    @ApiModelProperty(position = 2)
    private Double totalCost;

}
