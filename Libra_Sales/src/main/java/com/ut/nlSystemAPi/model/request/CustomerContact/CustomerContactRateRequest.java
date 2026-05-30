package com.ut.nlSystemAPi.model.request.CustomerContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerContactRateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long rating;
}