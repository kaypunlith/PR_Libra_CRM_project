package com.ut.nlSystemAPi.model.response.PayMethod;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayMethodResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 4)
    private String chartAccountName;

    @ApiModelProperty(position = 5)
    private String created;

    @ApiModelProperty(position = 6)
    private String createdBy;

    @ApiModelProperty(position = 7)
    private String modified;

    @ApiModelProperty(position = 8)
    private String modifiedBy;
}
