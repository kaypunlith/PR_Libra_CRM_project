package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardEmployeeTaskFilter extends Filter {

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 5)
    private String dateFrom;

    @ApiModelProperty(position = 6)
    private String dateTo;

    @ApiModelProperty(position = 7)
    private Long statusId;

    @ApiModelProperty(position = 8)
    private Long priorityId;

    @ApiModelProperty(position = 9)
    private Long privacyId;

    @ApiModelProperty(position = 10)
    private Long labelId;

}
