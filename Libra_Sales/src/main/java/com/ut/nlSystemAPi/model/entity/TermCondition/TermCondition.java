package com.ut.nlSystemAPi.model.entity.TermCondition;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermCondition extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long typeId;

    @ApiModelProperty(position = 4)
    private String typeName;

    @ApiModelProperty(position = 5)
    private String description;

}
