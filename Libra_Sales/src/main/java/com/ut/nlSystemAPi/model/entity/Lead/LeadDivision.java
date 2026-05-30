package com.ut.nlSystemAPi.model.entity.Lead;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadDivision {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long leadId;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private String makingProcess;

    @ApiModelProperty(position = 5)
    private Long negotiationId;
}
