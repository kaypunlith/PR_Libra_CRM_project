package com.ut.nlSystemAPi.model.response.FixedAsset;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FixedAssetFileResponse {
    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private String url;
}
