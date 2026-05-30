package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProvidentFundRequestStatus {

    @ApiModelProperty(position = 1)
    private Long providentFundId;

    @ApiModelProperty(position = 2, hidden = true)
    private Long modifiedBy;

    @ApiModelProperty(position = 3)
    private List<ProvidentFundRequestStatusByEmp> proFundUpdateStatus;

}
