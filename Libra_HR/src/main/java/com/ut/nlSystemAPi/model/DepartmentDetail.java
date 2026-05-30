package com.ut.nlSystemAPi.model;

import java.io.Serializable;

import com.ut.nlSystemAPi.model.base.BaseModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepartmentDetail extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long departmentId;

    @ApiModelProperty(position = 3)
    private String lats;

    @ApiModelProperty(position = 4)
    private String longs;

}
