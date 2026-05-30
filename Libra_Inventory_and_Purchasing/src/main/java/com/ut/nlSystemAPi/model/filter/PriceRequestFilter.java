package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PriceRequestFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Long organizationGroupId;

    @ApiModelProperty(position = 14)
    private Long createdBy;

    @ApiModelProperty(position = 15)
    private Long status;

    @ApiModelProperty(position = 16)
    private Long viewBy;

    @ApiModelProperty(position = 16)
    private Long viewByUser = 0L;
}
