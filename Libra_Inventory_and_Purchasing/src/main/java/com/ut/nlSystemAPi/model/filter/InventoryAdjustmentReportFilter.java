package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InventoryAdjustmentReportFilter extends Filter {

    @ApiModelProperty(position = 6)
    private String dateFrom;

    @ApiModelProperty(position = 7)
    private String dateTo;

    @ApiModelProperty(position = 8)
    private Long companyId;

    @ApiModelProperty(position = 9)
    private Long locationGroupId;

    @ApiModelProperty(position = 10)
    private Long productId;

    @ApiModelProperty(position = 11)
    private Long createdBy;


}
