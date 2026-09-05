package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityStageResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String pipelineSettingName;

    @ApiModelProperty(position = 3)
    private String stageName;

    @ApiModelProperty(position = 4)
    private Integer applyWithId;

    @ApiModelProperty(position = 5)
    private String applyWith;


    @ApiModelProperty(position = 5)
    private Long convertLead;

    @ApiModelProperty(position = 5)
    private Integer isUpload;

    @ApiModelProperty(position = 5)
    private Integer isPrint;
    @ApiModelProperty(position = 6)
    private String created;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private String modified;

    @ApiModelProperty(position = 9)
    private String modifiedBy;
}
