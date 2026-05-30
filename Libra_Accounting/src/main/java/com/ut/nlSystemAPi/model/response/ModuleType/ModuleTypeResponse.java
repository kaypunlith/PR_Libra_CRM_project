package com.ut.nlSystemAPi.model.response.ModuleType;

import com.ut.nlSystemAPi.model.Module;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ModuleTypeResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String nameOther;

    @ApiModelProperty(position = 4)
    private Long ordering;

    @ApiModelProperty(position = 5)
    private Integer status;

    @ApiModelProperty(position = 6)
    private String created;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private String modified;

    @ApiModelProperty(position = 9)
    private String modifiedBy;

    @ApiModelProperty(position = 10)
    private List<Module> moduleList;

}
