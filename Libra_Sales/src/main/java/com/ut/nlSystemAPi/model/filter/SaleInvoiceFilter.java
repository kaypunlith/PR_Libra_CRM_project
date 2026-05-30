package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaleInvoiceFilter extends Filter {

    @ApiModelProperty(position = 9)
    private Long type;

    @ApiModelProperty(position = 10)
    private String dateFrom;

    @ApiModelProperty(position = 11)
    private String dateTo;

    @ApiModelProperty(position = 11)
    private Integer organizationGroupId;

    @ApiModelProperty(position = 11)
    private Integer warehouseId;

    @ApiModelProperty(position = 11)
    private Integer organizationId;

    @ApiModelProperty(position = 12)
    private Integer status;

    @ApiModelProperty(position = 13)
    private Integer isPos;

    @ApiModelProperty(position = 14)
    private Integer indebted;

    @ApiModelProperty(position = 15)
    private Integer isDn;

}
