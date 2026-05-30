package com.ut.nlSystemAPi.model.request.Login.ProductPriceList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductListApproveStatus {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long status;
}
