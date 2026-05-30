package com.ut.nlSystemAPi.model.response.Uoms;

import com.ut.nlSystemAPi.model.ModuleType;
import com.ut.nlSystemAPi.model.RoleUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UomsResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String abbr;

    @ApiModelProperty(position = 5)
    private String description;

    @ApiModelProperty(position = 6)
    private String createdBy;

    @ApiModelProperty(position = 7)
    private String created;

    @ApiModelProperty(position = 8)
    private String modifiedBy;

    @ApiModelProperty(position = 9)
    private String modified;

}
