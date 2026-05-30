package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferOrderReportFilter extends Filter {

    @ApiModelProperty(position = 6)
    private String dateFrom;

    @ApiModelProperty(position = 7)
    private String dateTo;

    @ApiModelProperty(position = 8)
    private Long status;

    @ApiModelProperty(position = 9)
    private Long fromWarehouseId;

    @ApiModelProperty(position = 10)
    private Long toWarehouseId;

    @ApiModelProperty(position = 11)
    private Long createdBy;

}
