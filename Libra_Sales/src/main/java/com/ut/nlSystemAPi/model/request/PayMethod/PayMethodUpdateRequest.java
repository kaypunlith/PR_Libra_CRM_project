package com.ut.nlSystemAPi.model.request.PayMethod;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayMethodUpdateRequest extends PayMethodRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
