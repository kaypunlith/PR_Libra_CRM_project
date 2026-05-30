package com.ut.nlSystemAPi.model.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ut.nlSystemAPi.model.DepartmentUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepartmentRequest {

    @ApiModelProperty(position = 10, hidden = true)
    private Long id;

    @ApiModelProperty(position = 20)
    private Long settingGroupId;

    @ApiModelProperty(position = 30)
    private String name;

    @ApiModelProperty(position = 40)
    private Long provinceId;

    @ApiModelProperty(position = 50)
    private String description;

    @ApiModelProperty(position = 60)
    private String lats;

    @ApiModelProperty(position = 70)
    private String longs;

    @ApiModelProperty(position = 70)
    private String radius;

    @ApiModelProperty(position = 80)
    private List<LocationPointRequest> paths;

    @ApiModelProperty(position = 90)
    private List<Long> applyUsers;

}
