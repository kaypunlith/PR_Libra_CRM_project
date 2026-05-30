package com.ut.nlSystemAPi.model.response.Zone;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZoneResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String lats;

    @ApiModelProperty(position = 4)
    private String longs;

    @ApiModelProperty(position = 5)
    private String radius;

    @ApiModelProperty(position = 6)
    private String created;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private String modified;

    @ApiModelProperty(position = 9)
    private String modifiedBy;

    @ApiModelProperty(position = 10)
    private List<ZoneDetailResponse> paths;
}
