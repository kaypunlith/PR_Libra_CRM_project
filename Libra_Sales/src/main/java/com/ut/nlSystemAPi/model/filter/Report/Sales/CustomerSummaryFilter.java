package com.ut.nlSystemAPi.model.filter.Report.Sales;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerSummaryFilter extends Filter {

    @ApiModelProperty(position = 11)
    private List<String> years;

    @ApiModelProperty(position = 12)
    private Long organizationGroupId;

    @ApiModelProperty(position = 13)
    private Long organizationId;

}
