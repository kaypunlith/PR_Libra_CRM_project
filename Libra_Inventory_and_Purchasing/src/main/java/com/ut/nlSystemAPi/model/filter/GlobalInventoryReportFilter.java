package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GlobalInventoryReportFilter extends Filter {

    @ApiModelProperty(position = 6)
    private String dateFrom;

    @ApiModelProperty(position = 7)
    private String dateTo;

    @ApiModelProperty(position = 8)
    private Long companyId;

    @ApiModelProperty(position = 9)
    private Long warehouseId;

    @ApiModelProperty(position = 9)
    private Long locationId;

    @ApiModelProperty(position = 10)
    private Long productTypeId;

    @ApiModelProperty(position = 11)
    private Long qty;

    @ApiModelProperty(position = 11)
    private Long unitCost;

    @ApiModelProperty(position = 12)
    private Long productId;

    @ApiModelProperty(position = 12)
    private Long productGroupId;


}
