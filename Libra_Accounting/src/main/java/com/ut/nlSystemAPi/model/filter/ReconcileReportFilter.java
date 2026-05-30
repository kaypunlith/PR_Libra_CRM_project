package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReconcileReportFilter extends Filter {

    @ApiModelProperty(position = 8)
    private List<Long> branchId;

    @ApiModelProperty(position =12)
    private Long chartAccountId;

    @ApiModelProperty(position = 13)
    private Long ReconcileDateId;

    @ApiModelProperty(position = 14, hidden = true)
    private Long userId;

}
