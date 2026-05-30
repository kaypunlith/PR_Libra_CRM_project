package com.ut.nlSystemAPi.model.request.Login;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccessTokenRequest extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String username;

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

    @ApiModelProperty(position = 8)
    private String loginVerifyToken;
}
