package com.ut.nlSystemAPi.model.response.CustomerContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerContactDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long typeId;

    @ApiModelProperty(position = 3)
    private String typeName;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 6)
    private Double percent;

}