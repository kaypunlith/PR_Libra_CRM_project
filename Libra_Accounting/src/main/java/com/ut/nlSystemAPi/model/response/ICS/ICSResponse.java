package com.ut.nlSystemAPi.model.response.ICS;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ICSResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 4)
    private String chartAccountName;

    @ApiModelProperty(position = 5)
    private Long ordering;

}
