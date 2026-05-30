package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseBillFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Long poType;

    @ApiModelProperty(position = 14)
    private Long status;

    @ApiModelProperty(position = 15)
    private Long warehouseId;

    @ApiModelProperty(position = 16)
    private Long locationId;

    @ApiModelProperty(position = 17)
    private Long vendorId;

    @ApiModelProperty(position = 18)
    private Long type;

    @ApiModelProperty(position = 17)
    private Long viewByUser = 0L;
}
