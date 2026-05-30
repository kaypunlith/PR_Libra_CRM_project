package com.ut.nlSystemAPi.model.filter.Dropdown;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SalesInvoiceDropdownFilter extends Filter {

    @ApiModelProperty(position = 11)
    private Long chartAccountId;

    @ApiModelProperty(position = 12)
    private Long organizationId;

    @ApiModelProperty(position = 12)
    private Long saleOrderId;

    @ApiModelProperty(position = 13)
    private Long companyId;

    @ApiModelProperty(position = 14)
    private Long organizationGroupId;

    @ApiModelProperty(position = 14)
    private Long isMemo;

    @ApiModelProperty(position = 14)
    private Long type;

}
