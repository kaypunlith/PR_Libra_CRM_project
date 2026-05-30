package com.ut.nlSystemAPi.model.response.ProductGroup;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
@Data
public class ICSResponse {

    @ApiModelProperty(position = 1)
    private Long chartAccountId;

    @ApiModelProperty(position = 2)
    private String chartAccountName;

    @ApiModelProperty(position = 3)
    private Long accountTypeId;

    @ApiModelProperty(position = 4)
    private String accountTypeName;

}
