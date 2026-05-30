package com.ut.nlSystemAPi.model.response.ProductGroupPrice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
public class PgroupPriceCloneDetailResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Double fromCost;

    @ApiModelProperty(position = 3)
    private Double toCost;

    @ApiModelProperty(position = 4)
    private Long totalProduct;

    @ApiModelProperty(position = 4)
    private List<PgroupPriceTypeResponse> PgroupPriceTypeResponse;

}
