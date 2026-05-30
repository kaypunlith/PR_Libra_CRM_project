package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpportunityActivityResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String employeeGroupName;

    @ApiModelProperty(position = 3)
    private String stageName;

    @ApiModelProperty(position = 4)
    private String activityName;

    @ApiModelProperty(position = 5)
    private String taskName;

    @ApiModelProperty(position = 6)
    private String created;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private String modified;

    @ApiModelProperty(position = 9)
    private String modifiedBy;

    @ApiModelProperty(position = 10)
    private List<Long> employeeGroupIds;

    @ApiModelProperty(position = 11)
    private List<Long> stageIds;

    @ApiModelProperty(position = 12)
    private List<String> tasks;
}
