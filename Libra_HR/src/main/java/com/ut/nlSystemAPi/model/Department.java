package com.ut.nlSystemAPi.model;

import java.io.Serializable;
import java.util.List;

import com.ut.nlSystemAPi.model.base.BaseModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Department extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long settingGroupId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private Long userApply;

    @ApiModelProperty(position = 6)
    private Long provinceId;

    @ApiModelProperty(position = 70)
    private String code;

    @ApiModelProperty(position = 80)
    private String lats;

    @ApiModelProperty(position = 90)
    private String longs;

    @ApiModelProperty(position = 100)
    private List<DepartmentUser> departmentUserList;

    @ApiModelProperty(position = 110)
    private String radius;


}