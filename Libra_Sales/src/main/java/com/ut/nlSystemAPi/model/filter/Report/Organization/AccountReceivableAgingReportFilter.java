package com.ut.nlSystemAPi.model.filter.Report.Organization;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountReceivableAgingReportFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String date;

    @ApiModelProperty(position = 12)
    private Long intervalDay;

    @ApiModelProperty(position = 13)
    private Long throughDay;

    @ApiModelProperty(position = 14)
    private Long customerId;

    @ApiModelProperty(position = 15)
    private Long classId;

    @ApiModelProperty(position = 16)
    private Long cgroupId;

}
