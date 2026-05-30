package com.ut.nlSystemAPi.model.response.Commission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long productId;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 4)
    private String sku;

    @ApiModelProperty(position = 5)
    private String upc;

    @ApiModelProperty(position = 6)
    private Double qty;

    @ApiModelProperty(position = 7)
    private Long uomId;

    @ApiModelProperty(position = 8)
    private String uomName;

    @ApiModelProperty(position = 9)
    private String uomAbbr;

    @ApiModelProperty(position = 10)
    private Double amount;

    @ApiModelProperty(position = 11)
    private Double percent;

    @ApiModelProperty(position = 12)
    private Double unitPrice;
}

