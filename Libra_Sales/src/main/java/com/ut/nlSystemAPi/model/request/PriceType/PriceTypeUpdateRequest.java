package com.ut.nlSystemAPi.model.request.PriceType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceTypeUpdateRequest extends PriceTypeRequest{

    @ApiModelProperty(position = 1)
    private Long id;

}
