package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String name;


    @ApiModelProperty(position = 4)
    private String nameEn;

    @ApiModelProperty(position = 5)
    private String nameKh;
    @ApiModelProperty(position = 4)
    private String telephone;

    @ApiModelProperty(position = 5)
    private Long vendorId;

    @ApiModelProperty(position = 6)
    private String vendorName;

    @ApiModelProperty(position = 5)
    private Long status;

}

