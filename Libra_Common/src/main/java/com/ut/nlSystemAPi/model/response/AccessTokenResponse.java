package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccessTokenResponse {

    @ApiModelProperty(position = 3)
    private String accessToken;

    @ApiModelProperty(position = 4)
    private String tokenType;

    @ApiModelProperty(position = 5)
    private String refreshToken;

    @ApiModelProperty(position = 6)
    private Long expiresIn;

    @ApiModelProperty(position = 7)
    private String scope;
}
