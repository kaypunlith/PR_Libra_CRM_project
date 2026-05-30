package com.ut.nlSystemAPi.model.request.Login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UomsUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String abbr;

    @ApiModelProperty(position = 5)
    private String description;
}
