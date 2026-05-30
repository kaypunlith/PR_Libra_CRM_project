package com.ut.nlSystemAPi.model.request.CustomerContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerContactProgressRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Double percent;

}