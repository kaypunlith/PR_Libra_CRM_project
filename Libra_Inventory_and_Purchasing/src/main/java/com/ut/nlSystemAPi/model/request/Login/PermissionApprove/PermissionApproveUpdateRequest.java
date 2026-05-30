package com.ut.nlSystemAPi.model.request.Login.PermissionApprove;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PermissionApproveUpdateRequest{

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long type;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Double fromAmount;

    @ApiModelProperty(position = 4)
    private Double toAmount;

    @ApiModelProperty(position = 5)
    private Long isCashAdvance;

    @ApiModelProperty(position = 6)
    private Long isCod;

    @ApiModelProperty(position = 7)
    private List<Long> userList;

    @ApiModelProperty(position = 8)
    private List<Long> departmentList;

}
