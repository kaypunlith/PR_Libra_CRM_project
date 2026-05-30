package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckUserLogin {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String hostName;

    @ApiModelProperty(position = 3)
    private String hostAddress;

    @ApiModelProperty(position = 4)
    private String verifyChaptchar;

    @ApiModelProperty(position = 5)
    private Long numberOfLogin;
}
