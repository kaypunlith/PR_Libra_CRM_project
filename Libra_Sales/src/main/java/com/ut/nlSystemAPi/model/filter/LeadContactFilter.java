package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadContactFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long leadId;
}
