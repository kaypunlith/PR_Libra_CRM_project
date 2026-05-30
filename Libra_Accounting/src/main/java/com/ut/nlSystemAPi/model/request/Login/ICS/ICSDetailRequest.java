package com.ut.nlSystemAPi.model.request.Login.ICS;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ICSDetailRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long chartAccountId;

}
