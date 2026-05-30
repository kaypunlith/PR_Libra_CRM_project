package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeCalendarFilter extends Filter {

    @ApiModelProperty(position = 5)
    private Long employeeId;

    @ApiModelProperty(position = 6)
    private String dateFrom;

    @ApiModelProperty(position = 7)
    private String dateTo;

    @ApiModelProperty(position = 8)
    private Integer isRepeatable;

    @ApiModelProperty(position = 9)
    private Integer repeatType;
}
