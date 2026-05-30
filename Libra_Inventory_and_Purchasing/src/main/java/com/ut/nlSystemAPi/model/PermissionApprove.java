package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PermissionApprove extends BaseModel {

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

    @ApiModelProperty(position = 9)
    private List<Long> userList;

}
