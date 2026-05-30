package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrialBalanceResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private String accountCode;

    @ApiModelProperty(position = 1)
    private String accountDescription;

    @ApiModelProperty(position = 1)
    private String accountGroup;

    @ApiModelProperty(position = 1)
    private List<TrialBalanceDetails> trialBalanceDetails;
}
