package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpportunityActivityRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private List<Long> employeeGroupIds;

    @ApiModelProperty(position = 3)
    private List<Long> stageIds;

    @ApiModelProperty(position = 4)
    private List<String> tasks;
}
