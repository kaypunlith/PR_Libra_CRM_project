package com.ut.nlSystemAPi.model.response.UserMobile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserMobileResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String username;

    @ApiModelProperty(position = 3)
    private Long userMobileDeleteReasonId;

    @ApiModelProperty(position = 3)
    private String userMobileDeleteReasonName;

    @ApiModelProperty(position = 4)
    private Long type;

    @ApiModelProperty(position = 5)
    private Long status;

    @ApiModelProperty(position = 6)
    private String approvedBy;

    @ApiModelProperty(position = 7)
    private String approved;

    @ApiModelProperty(position = 8)
    private String rejectedBy;

    @ApiModelProperty(position = 9)
    private String rejected;

    @ApiModelProperty(position = 10)
    private String created;

    @ApiModelProperty(position = 11)
    private String createdBy;

}
