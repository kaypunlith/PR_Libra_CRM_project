package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PublicHolidayFilter extends Filter {
    @ApiModelProperty(position = 1)
    private String year;
}
