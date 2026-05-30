package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Nationality extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long regionId;

    @ApiModelProperty(position = 3)
    private String nameKh;

    @ApiModelProperty(position = 4)
    private String nameEn;

    @ApiModelProperty(position = 5)
    private String nameCn;
}
