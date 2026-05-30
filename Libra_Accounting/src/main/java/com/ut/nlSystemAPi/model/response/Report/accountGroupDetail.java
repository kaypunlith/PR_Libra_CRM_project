package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class accountGroupDetail {
    @ApiModelProperty(position = 1)
    private Long accountGroupId;

    @ApiModelProperty(position = 1)
    private String accountGroupIds;

    @ApiModelProperty(position = 1)
    private String accountGroupName;

    @ApiModelProperty(position = 1)
    private Double totalAmount;

    @ApiModelProperty(position = 1)
    private List<chartAccountDetail> chartAccountDetails;








}
