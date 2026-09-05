package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityStageRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private Integer applyWith;

    @ApiModelProperty(position = 2)
    private Long convertLead;

    @ApiModelProperty(position = 3)
    private Integer isUpload;

    @ApiModelProperty(position = 4)
    private Integer isPrint;
}
