package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VoucherFilter extends Filter {

    @ApiModelProperty(position = 14)
    private String dateFrom;

    @ApiModelProperty(position = 15)
    private String dateTo;

    @ApiModelProperty(position = 16)
    private Long branchId;

    @ApiModelProperty(position = 17)
    private Integer status;

    @ApiModelProperty(position = 18)
    private Integer voucherType;

    @ApiModelProperty(position = 19)
    private Integer userType;
}
