package com.ut.nlSystemAPi.model.entity.CrmCategory;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrmCategory extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String photo;
}
