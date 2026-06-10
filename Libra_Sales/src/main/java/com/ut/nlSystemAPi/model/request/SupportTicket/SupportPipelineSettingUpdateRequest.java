package com.ut.nlSystemAPi.model.request.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportPipelineSettingUpdateRequest extends SupportPipelineSettingRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
