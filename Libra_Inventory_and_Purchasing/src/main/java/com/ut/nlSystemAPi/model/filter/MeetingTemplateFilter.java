package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MeetingTemplateFilter extends Filter {
    @ApiModelProperty(position = 5)
    private String dateFrom;

    @ApiModelProperty(position = 6)
    private String dateTo;

    @ApiModelProperty(position = 7)
    private Long createdBy;

}
