package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PipelineSettingResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String description;

    @ApiModelProperty(position = 3)
    private String employeeGroupName;

    @ApiModelProperty(position = 4)
    private String stageName;

    @ApiModelProperty(position = 5)
    private String percentage;

    @ApiModelProperty(position = 6)
    private String ordering;

    @ApiModelProperty(position = 7)
    private String created;

    @ApiModelProperty(position = 8)
    private String createdBy;

    @ApiModelProperty(position = 9)
    private String modified;

    @ApiModelProperty(position = 10)
    private String modifiedBy;

    @ApiModelProperty(position = 11)
    private List<PipelineSettingStageResponse> stages;
}
