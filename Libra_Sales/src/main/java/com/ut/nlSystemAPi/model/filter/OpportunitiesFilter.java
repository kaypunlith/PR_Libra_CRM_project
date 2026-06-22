package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunitiesFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long pipelineId;

    @ApiModelProperty(position = 11)
    private Long createdBy;

    @ApiModelProperty(position = 12)
    private Integer status;

    @ApiModelProperty(position = 13)
    private Long organizationId;

    @ApiModelProperty(position = 14)
    private String dateFrom;

    @ApiModelProperty(position = 15)
    private String dateTo;

    @ApiModelProperty(position = 16)
    private Long stageId;
}
