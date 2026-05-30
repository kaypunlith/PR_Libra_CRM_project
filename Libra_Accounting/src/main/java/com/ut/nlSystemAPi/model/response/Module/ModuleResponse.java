package com.ut.nlSystemAPi.model.response.Module;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModuleResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long moduleTypeId;

    @ApiModelProperty(position = 3)
    private String moduleTypeName;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private Long ordering;

    @ApiModelProperty(position = 6)
    private Integer status;

    @ApiModelProperty(position = 7)
    private String created;

    @ApiModelProperty(position = 8)
    private String createdBy;

    @ApiModelProperty(position = 9)
    private String modified;

    @ApiModelProperty(position = 10)
    private String modifiedBy;

}
