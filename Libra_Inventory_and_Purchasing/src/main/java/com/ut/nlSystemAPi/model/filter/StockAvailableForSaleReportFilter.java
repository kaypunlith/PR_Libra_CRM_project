package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockAvailableForSaleReportFilter extends Filter {

    @ApiModelProperty(position = 6)
    private String dateFrom;

    @ApiModelProperty(position = 7)
    private String dateTo;

    @ApiModelProperty(position = 9)
    private Long warehouseId;

    @ApiModelProperty(position = 10)
    private Long productId;

    @ApiModelProperty(position = 10)
    private Long productGroupId;

    @ApiModelProperty(position = 11)
    private Long productPriceListId;


}
