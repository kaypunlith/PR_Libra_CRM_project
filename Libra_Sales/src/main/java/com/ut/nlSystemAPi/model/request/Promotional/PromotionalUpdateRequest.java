package com.ut.nlSystemAPi.model.request.Promotional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PromotionalUpdateRequest extends PromotionalRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
