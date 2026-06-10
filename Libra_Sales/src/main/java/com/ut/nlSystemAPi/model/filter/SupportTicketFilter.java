package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTicketFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long pipelineId;

    @ApiModelProperty(position = 11)
    private Long assignedTo;

    @ApiModelProperty(position = 12)
    private Long createdBy;

    @ApiModelProperty(position = 13)
    private Integer status;

    @ApiModelProperty(position = 14)
    private Long organizationId;

    @ApiModelProperty(position = 15)
    private String dateFrom;

    @ApiModelProperty(position = 16)
    private String dateTo;
}
