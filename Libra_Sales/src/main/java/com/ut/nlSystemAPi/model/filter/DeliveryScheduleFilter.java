package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryScheduleFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String code;

    @ApiModelProperty(position = 12)
    private String dateFrom;

    @ApiModelProperty(position = 13)
    private String dateTo;

}
