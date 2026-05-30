package com.ut.nlSystemAPi.model.request.TermCondition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class TermConditionRequest {

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private Long typeId;

    @ApiModelProperty(position = 2)
    private String description;
}
