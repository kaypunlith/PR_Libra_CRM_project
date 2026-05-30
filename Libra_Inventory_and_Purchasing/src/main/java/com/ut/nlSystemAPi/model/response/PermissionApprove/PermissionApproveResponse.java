package com.ut.nlSystemAPi.model.response.PermissionApprove;

import com.ut.nlSystemAPi.model.response.Dropdown.DepartmentDropDownResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PermissionApproveResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long type;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Double fromAmount;

    @ApiModelProperty(position = 5)
    private Double toAmount;

    @ApiModelProperty(position = 6)
    private Long isCashAdvance;

    @ApiModelProperty(position = 7)
    private Long isCod;

    @ApiModelProperty(position = 8)
    private String createdBy;

    @ApiModelProperty(position = 9)
    private String created;

    @ApiModelProperty(position = 10)
    private String modifiedBy;

    @ApiModelProperty(position = 11)
    private String modified;

    @ApiModelProperty(position = 12)
    private List<UserPermissionApproveResponse> userList;

    @ApiModelProperty(position = 13)
    private List<DepartmentDropDownResponse> departmentList;

}
