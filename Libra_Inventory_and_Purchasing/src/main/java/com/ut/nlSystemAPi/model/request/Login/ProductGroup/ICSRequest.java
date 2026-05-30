package com.ut.nlSystemAPi.model.request.Login.ProductGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ICSRequest {

    @ApiModelProperty(position = 1)
    private Long chartAccountType;

    @ApiModelProperty(position = 2)
    private Long chartAccountId;
}
