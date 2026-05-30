package com.ut.nlSystemAPi.model.entity.TermConditionApply;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermConditionApply extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long moduleTypeId;

    @ApiModelProperty(position = 4)
    private Long termConditionTypeId;

    @ApiModelProperty(position = 6)
    private Long termConditionId;

}
