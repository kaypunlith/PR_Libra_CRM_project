package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestStockFilter extends Filter {
    @ApiModelProperty(position = 11)
    private String date;

    @ApiModelProperty(position = 12)
    private Long warehouseId;

    @ApiModelProperty(position = 12)
    private Long status;

    @ApiModelProperty(position = 16)
    private Long viewByUser = 0L;

}
