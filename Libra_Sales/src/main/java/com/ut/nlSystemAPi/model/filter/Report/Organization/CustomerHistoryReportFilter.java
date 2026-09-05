package com.ut.nlSystemAPi.model.filter.Report.Organization;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerHistoryReportFilter extends Filter {
    @ApiModelProperty(position = 1)
    private String fromDate;

    @ApiModelProperty(position = 2)
    private String toDate;

    @ApiModelProperty(position = 3, example = "1")
    private Long customerId;

    @ApiModelProperty(position = 4, example = "1")
    private Long type;

    @ApiModelProperty(position = 5, example = "Memo done")
    private String memo;
}
