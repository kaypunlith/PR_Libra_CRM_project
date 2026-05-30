package com.ut.nlSystemAPi.model.request.Section;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SectionUpdateRequest extends SectionRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}