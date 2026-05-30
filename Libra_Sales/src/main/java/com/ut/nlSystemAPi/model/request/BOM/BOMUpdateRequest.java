package com.ut.nlSystemAPi.model.request.BOM;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BOMUpdateRequest extends BOMRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}
