package com.ut.nlSystemAPi.model.request.CustomerContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerContactDescriptionRequest {

    @ApiModelProperty(position = 1)
    private Long typeId;

    @ApiModelProperty(position = 2)
    private String description;

}