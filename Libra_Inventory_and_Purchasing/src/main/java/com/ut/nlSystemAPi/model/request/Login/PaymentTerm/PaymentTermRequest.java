package com.ut.nlSystemAPi.model.request.Login.PaymentTerm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaymentTermRequest {

    @ApiModelProperty(position = 1)
    private Long typeId;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long netDay;
}
