package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReconcileEndingDateReportFilter extends Filter {

    @ApiModelProperty(position = 10, hidden = true)
    private Long companyId;

    @ApiModelProperty(position =12)
    private Long chartAccountId;

    @ApiModelProperty(position =12)
    private Long branchId;

}
