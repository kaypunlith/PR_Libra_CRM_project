package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GroupResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long settingGroupId;

    @ApiModelProperty(position = 2)
    private String settingGroupName;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private String modifiedDate;

    @ApiModelProperty(position = 4)
    private List<DepartmentListResponse> departmentList;

}
