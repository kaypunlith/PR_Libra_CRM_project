package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long companyId;

    @ApiModelProperty(position = 11)
    private Long leadGroupId;

    @ApiModelProperty(position = 12)
    private Long createdBy;

    @ApiModelProperty(position = 13)
    private Integer activityStatus;

    @ApiModelProperty(position = 14)
    private String dateFrom;

    @ApiModelProperty(position = 15)
    private String dateTo;

    @ApiModelProperty(position = 15)
    private Long sourceId;

}
