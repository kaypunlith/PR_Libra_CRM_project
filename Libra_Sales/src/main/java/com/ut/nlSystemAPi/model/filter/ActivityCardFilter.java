package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityCardFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long customerId;

    @ApiModelProperty(position = 11)
    private Long saleTargetId;

    @ApiModelProperty(position = 12, notes = "Employee id from sale target employee")
    private Long saleTargetEmployeeId;

    @ApiModelProperty(position = 13)
    private String dateFrom;

    @ApiModelProperty(position = 14)
    private String dateTo;
}
