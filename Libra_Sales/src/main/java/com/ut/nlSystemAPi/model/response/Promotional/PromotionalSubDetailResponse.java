package com.ut.nlSystemAPi.model.response.Promotional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PromotionalSubDetailResponse {

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
    private Long uomId;

    @ApiModelProperty(position = 7)
    private String uomName;

    @ApiModelProperty(position = 8)
    private String uomAbbr;

    @ApiModelProperty(position = 9)
    private Double qty;
}
