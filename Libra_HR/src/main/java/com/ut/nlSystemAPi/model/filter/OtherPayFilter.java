package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtherPayFilter extends Filter {
    @ApiModelProperty(position = 1)
    private String date;

    @ApiModelProperty(position = 1)
    private Long payrollId;

    @ApiModelProperty(position = 1)
    private Long employeeId;
}
