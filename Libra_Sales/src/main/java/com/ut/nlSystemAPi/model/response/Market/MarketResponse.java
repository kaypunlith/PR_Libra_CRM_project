package com.ut.nlSystemAPi.model.response.Market;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MarketResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long zoneId;

    @ApiModelProperty(position = 4)
    private String zoneName;

    @ApiModelProperty(position = 6)
    private String created;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private String modified;

    @ApiModelProperty(position = 9)
    private String modifiedBy;
}
