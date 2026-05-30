package com.ut.nlSystemAPi.model.response.TermConditionApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermConditionApplyResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long moduleTypeId;

    @ApiModelProperty(position = 3)
    private String moduleTypeName;

    @ApiModelProperty(position = 4)
    private Long termConditionTypeId;

    @ApiModelProperty(position = 5)
    private String termConditionTypeName;

    @ApiModelProperty(position = 6)
    private Long termConditionDefaultId;

    @ApiModelProperty(position = 7)
    private String termConditionDefaultName;

}
