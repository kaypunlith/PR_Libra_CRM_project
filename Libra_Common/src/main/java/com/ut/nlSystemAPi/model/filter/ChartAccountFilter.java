package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChartAccountFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long companyId;

    @ApiModelProperty(position = 10)
    private Long accountTypeId;

    @ApiModelProperty(position = 10)
    private Long accountGroupId;

    @ApiModelProperty(position = 10, hidden = true)
    private Long parentId;

    @ApiModelProperty(position = 10)
    private Long branchId;

}
