package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseReceiveFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long purchasing;

    @ApiModelProperty(position = 10)
    private Long warehouseId;

    @ApiModelProperty(position = 11)
    private Long locationId;

    @ApiModelProperty(position = 12)
    private Long status;

    @ApiModelProperty(position = 14, hidden = true)
    private Long userId;

}
