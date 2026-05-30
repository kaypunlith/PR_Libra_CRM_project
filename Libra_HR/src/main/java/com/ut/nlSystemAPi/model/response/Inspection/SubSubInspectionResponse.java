package com.ut.nlSystemAPi.model.response.Inspection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubSubInspectionResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long subInspectionGroupId;

    @ApiModelProperty(position =6)
    private List<Long> subSubstaticScore;

    @ApiModelProperty(position = 7)
    private Long score;

}
