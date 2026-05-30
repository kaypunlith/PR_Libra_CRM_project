package com.ut.nlSystemAPi.model.request.CMTerm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CMTermUpdateRequest extends CMTermRequest {
    @ApiModelProperty(position = 1)
    private Long id;
}
