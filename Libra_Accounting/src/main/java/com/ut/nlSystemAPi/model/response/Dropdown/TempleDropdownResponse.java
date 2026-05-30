package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TempleDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String nameKh;

    @ApiModelProperty(position = 3)
    private String nameEn;

    @ApiModelProperty(position = 4)
    private String nameCn;

}
