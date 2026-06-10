package com.ut.nlSystemAPi.model.response.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportStageResponse {

    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private String pipelineSettingName;
    @ApiModelProperty(position = 3)
    private String stageName;
    @ApiModelProperty(position = 4)
    private String created;
    @ApiModelProperty(position = 5)
    private String createdBy;
    @ApiModelProperty(position = 6)
    private String modified;
    @ApiModelProperty(position = 7)
    private String modifiedBy;
}
