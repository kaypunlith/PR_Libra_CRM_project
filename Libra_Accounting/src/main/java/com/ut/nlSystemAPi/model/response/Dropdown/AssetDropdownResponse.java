package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssetDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private Long fixAssetGroupId;
    @ApiModelProperty(position = 3)
    private String fixAssetGroupName;
    @ApiModelProperty(position = 4)
    private String code;
    @ApiModelProperty(position = 5)
    private String name;
    @ApiModelProperty(position = 6)
    private String barcode;
    @ApiModelProperty(position = 7)
    private Long uomId;
    @ApiModelProperty(position = 8)
    private String uomName;
    @ApiModelProperty(position = 9)
    private String spec;
    @ApiModelProperty(position = 10)
    private String model;
}
