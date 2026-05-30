package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReconcileFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long companyId;

    @ApiModelProperty(position = 11)
    private Long chartAccountId;

    @ApiModelProperty(position = 12)
    private Long branchId;

    @ApiModelProperty(position = 13)
    private Long isHideFutureData;

    @ApiModelProperty(position = 14)
    private String dateFrom;

    @ApiModelProperty(position = 15)
    private String dateTo;

}
