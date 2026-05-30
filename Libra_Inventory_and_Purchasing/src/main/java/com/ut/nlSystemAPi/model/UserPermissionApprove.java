package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserPermissionApprove {

    @ApiModelProperty(position = 2)
    private Long userId;

    @ApiModelProperty(position = 3)
    private Long permissionApproveId;

}
