package com.ut.nlSystemAPi.model.response.PermissionApprove;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepartmentPermissionApproveResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String name;

}
