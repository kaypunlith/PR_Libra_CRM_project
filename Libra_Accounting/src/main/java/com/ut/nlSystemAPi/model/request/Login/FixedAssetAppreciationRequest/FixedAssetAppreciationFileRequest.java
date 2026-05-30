package com.ut.nlSystemAPi.model.request.Login.FixedAssetAppreciationRequest;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class FixedAssetAppreciationFileRequest {
    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String url;

    @ApiModelProperty(position = 4, hidden = true)
    private String modified;

    @ApiModelProperty(position = 5, hidden = true)
    private Long modifiedBy;

    @ApiModelProperty(position = 6, hidden = true)
    private Long createdBy;

    @ApiModelProperty(position = 7, hidden = true)
    private String created;

}
