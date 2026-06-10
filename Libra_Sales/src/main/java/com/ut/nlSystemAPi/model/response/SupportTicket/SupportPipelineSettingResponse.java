package com.ut.nlSystemAPi.model.response.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SupportPipelineSettingResponse {

    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private Long typeId;
    @ApiModelProperty(position = 3)
    private String typeName;
    @ApiModelProperty(position = 4)
    private String description;
    @ApiModelProperty(position = 5)
    private Integer resolutionNumber;
    @ApiModelProperty(position = 6)
    private Long baseOnId;
    @ApiModelProperty(position = 7)
    private String baseOnName;
    @ApiModelProperty(position = 8)
    private String employeeGroupName;
    @ApiModelProperty(position = 9)
    private String stageName;
    @ApiModelProperty(position = 10)
    private String percentage;
    @ApiModelProperty(position = 11)
    private String ordering;
    @ApiModelProperty(position = 12)
    private String created;
    @ApiModelProperty(position = 13)
    private String createdBy;
    @ApiModelProperty(position = 14)
    private String modified;
    @ApiModelProperty(position = 15)
    private String modifiedBy;
    @ApiModelProperty(position = 16)
    private List<SupportPipelineSettingStageResponse> stages;
}
