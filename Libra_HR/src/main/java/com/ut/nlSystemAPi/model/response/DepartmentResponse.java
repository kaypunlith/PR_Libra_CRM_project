package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DepartmentResponse implements Serializable {

    @ApiModelProperty(position = 10)
    private Long id;

    @ApiModelProperty(position = 20)
    private Long settingGroupId;

    @ApiModelProperty(position = 21)
    private String settingGroupName;

    @ApiModelProperty(position = 30)
    private String name;

    @ApiModelProperty(position = 40)
    private String description;

    @ApiModelProperty(position = 41)
    private String code;

    @ApiModelProperty(position = 42)
    private String lats;

    @ApiModelProperty(position = 43)
    private String longs;

    @ApiModelProperty(position = 44)
    private String radius;

    @ApiModelProperty(position = 50)
    private Long provinceId;

    @ApiModelProperty(position = 60)
    private String provinceName;

    @ApiModelProperty(position = 70)
    private String modifiedDate;

    @ApiModelProperty(position = 80)
    private List<LocationPointResponse> paths;

    @ApiModelProperty(position = 90)
    private List<UserDepartment> applyUsers;



}