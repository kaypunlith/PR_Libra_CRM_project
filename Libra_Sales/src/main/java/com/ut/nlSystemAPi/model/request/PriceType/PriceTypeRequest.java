package com.ut.nlSystemAPi.model.request.PriceType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceTypeRequest {

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long ordering;

    @ApiModelProperty(position = 4)
    private Long applyTo;

    @ApiModelProperty(position = 5)
    private Long isShowCatalogue;
}
