package com.ut.nlSystemAPi.config.authenticator;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCodeDto {

    @ApiModelProperty(position = 1)
    private String code;

    @ApiModelProperty(position = 2)
    private String username;

    @ApiModelProperty(position = 3)
    private String loginVerifyToken;
}
