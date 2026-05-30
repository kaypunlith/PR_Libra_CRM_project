package com.ut.nlSystemAPi.model.request.Login.UomConverRequest;

import com.ut.nlSystemAPi.model.response.UomConversion.UomConversionList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class UomConversionUpdateRequest {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long mainUom;

    @ApiModelProperty(position = 3)
    private Long smallUom;

    @ApiModelProperty(position = 4)
    private Long value;

    @ApiModelProperty(position = 5)
    private List<UomConversionList> otherUoms;
}
