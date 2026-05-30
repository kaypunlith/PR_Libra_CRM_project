package com.ut.nlSystemAPi.model.request.PayMethod;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayMethodRequest {

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;
}
