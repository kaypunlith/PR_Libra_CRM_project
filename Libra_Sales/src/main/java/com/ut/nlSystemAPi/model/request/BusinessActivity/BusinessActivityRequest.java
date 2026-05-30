package com.ut.nlSystemAPi.model.request.BusinessActivity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusinessActivityRequest {

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String abbr;
}