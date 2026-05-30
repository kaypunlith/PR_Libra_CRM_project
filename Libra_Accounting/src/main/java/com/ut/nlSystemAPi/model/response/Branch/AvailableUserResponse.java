package com.ut.nlSystemAPi.model.response.Branch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AvailableUserResponse {
    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private String userName;
}
