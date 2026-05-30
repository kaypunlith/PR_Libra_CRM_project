package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PipelineSettingUpdateRequest extends PipelineSettingRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
