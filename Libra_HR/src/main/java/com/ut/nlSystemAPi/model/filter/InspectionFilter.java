package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionFilter extends Filter {

    @ApiModelProperty(position = 100)
    private String startDate;

    @ApiModelProperty(position = 101)
    private String toDate;

    @ApiModelProperty(position = 102)
    private Long groupId;

    @ApiModelProperty(position = 103)
    private Long departmentId;

    @ApiModelProperty(position = 104)
    private Long provinceId;

    @ApiModelProperty(position = 105)
    private Long year;

}
