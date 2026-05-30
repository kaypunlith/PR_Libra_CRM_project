package com.ut.nlSystemAPi.model.response.BusinessActivity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusinessActivityResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String abbr;
}