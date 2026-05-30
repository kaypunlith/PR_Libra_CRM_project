package com.ut.nlSystemAPi.model.response.PaymentTerm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaymentTermResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long typeId;

    @ApiModelProperty(position = 2)
    private String typeName;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String netDay;

    @ApiModelProperty(position = 5)
    private String createdBy;

    @ApiModelProperty(position = 6)
    private String created;

    @ApiModelProperty(position = 7)
    private String modifiedBy;

    @ApiModelProperty(position = 8)
    private String modified;
}
