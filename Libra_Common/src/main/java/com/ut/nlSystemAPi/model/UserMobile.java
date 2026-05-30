package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserMobile extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String username;

    @ApiModelProperty(position = 3)
    private Long userMobileDeleteReasonId;

    @ApiModelProperty(position = 4)
    private Long type;

    @ApiModelProperty(position = 6)
    private Long approvedBy;

    @ApiModelProperty(position = 7)
    private String approved;

    @ApiModelProperty(position = 8)
    private Long rejectedBy;

    @ApiModelProperty(position = 9)
    private String rejected;

}
