package com.ut.nlSystemAPi.model.request.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SupportPipelineSettingRequest {

    @ApiModelProperty(position = 1)
    private Long typeId;

    @ApiModelProperty(position = 2)
    private String description;

    @ApiModelProperty(position = 3)
    private Integer resolutionNumber;

    @ApiModelProperty(position = 4)
    private Long baseOnId;

    @ApiModelProperty(position = 5)
    private List<Long> employeeGroupIds;

    @ApiModelProperty(position = 6)
    private List<SupportPipelineSettingStageRequest> stages;
}
