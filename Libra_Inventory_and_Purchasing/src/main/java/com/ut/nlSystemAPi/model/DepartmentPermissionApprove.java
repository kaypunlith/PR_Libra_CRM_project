package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepartmentPermissionApprove {

    @ApiModelProperty(position = 2)
    private Long departmentId;

    @ApiModelProperty(position = 3)
    private Long permissionApproveId;

}
