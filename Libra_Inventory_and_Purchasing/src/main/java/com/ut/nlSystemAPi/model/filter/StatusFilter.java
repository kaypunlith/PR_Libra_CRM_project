package com.ut.nlSystemAPi.model.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StatusFilter extends BaseFilter {

    @ApiModelProperty(position = 11)
    private Long isClose;

    @ApiModelProperty(position = 12)
    private Long isApproved;

}
