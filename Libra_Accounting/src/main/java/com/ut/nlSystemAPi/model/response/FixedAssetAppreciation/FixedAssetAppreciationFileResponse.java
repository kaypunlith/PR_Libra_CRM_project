package com.ut.nlSystemAPi.model.response.FixedAssetAppreciation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FixedAssetAppreciationFileResponse {
    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private String url;
}
