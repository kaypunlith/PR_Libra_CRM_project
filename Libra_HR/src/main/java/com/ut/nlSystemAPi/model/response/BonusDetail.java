package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BonusDetail {

    @ApiModelProperty(position = 1)
    private Long branchId;

    @ApiModelProperty(position = 2)
    private String branchName;

    @ApiModelProperty(position = 2)
    private Float amount;


}
