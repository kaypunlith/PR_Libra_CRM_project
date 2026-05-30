package com.ut.nlSystemAPi.model.response.UomConversion;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UomConversionResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String mainUomName;

    @ApiModelProperty(position = 2)
    private Long mainUomId;

    @ApiModelProperty(position = 4)
    private Long smallUomId;

    @ApiModelProperty(position = 5)
    private String smallUomName;

    @ApiModelProperty(position = 6)
    private Long value;

    @ApiModelProperty(position = 7)
    private String createBy;

    @ApiModelProperty(position = 8)
    private String created;

    @ApiModelProperty(position = 7)
    private String modifiedBy;

    @ApiModelProperty(position = 8)
    private String modified;

    @ApiModelProperty(position = 8)
    private List<UomConversionDetailResponse> otherUoms;
}
