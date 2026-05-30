package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtRequestFilter extends Filter {
    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 1)
    private String dateFrom;

    @ApiModelProperty(position = 2)
    private String dateTo;

    @ApiModelProperty(position = 3)
    private Long status;
}
