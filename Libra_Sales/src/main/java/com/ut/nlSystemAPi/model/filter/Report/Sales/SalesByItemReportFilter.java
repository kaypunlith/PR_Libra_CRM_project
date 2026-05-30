package com.ut.nlSystemAPi.model.filter.Report.Sales;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesByItemReportFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Integer view;

    @ApiModelProperty(position = 10)
    private Long type;

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Integer status;

    @ApiModelProperty(position = 14)
    private Long productGroupId;

    @ApiModelProperty(position = 15)
    private Long productId;

    @ApiModelProperty(position = 17)
    private Long warehouseId;

}