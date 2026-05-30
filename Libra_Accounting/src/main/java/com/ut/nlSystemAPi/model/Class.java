package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Class extends BaseModel implements Serializable {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long companyName;

    @ApiModelProperty(position = 4)
    private Long userId;

    @ApiModelProperty(position = 5)
    private String userName;

    @ApiModelProperty(position = 6)
    private String name;

    @ApiModelProperty(position = 7)
    private String description;

    @ApiModelProperty(position = 8)
    private Long subIdOfClass;

}
