package com.ut.nlSystemAPi.model.request.Login.PrivacyConditions;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PrivacyConditionsRequest {
    @ApiModelProperty(position = 2)
    private String privacyPolicyKh;

    @ApiModelProperty(position = 3)
    private String privacyPolicyEn;

    @ApiModelProperty(position = 4)
    private String privacyPolicyCn;

    @ApiModelProperty(position = 5)
    private String termOfConditionKh;

    @ApiModelProperty(position = 6)
    private String termOfConditionEn;

    @ApiModelProperty(position = 7)
    private String termOfConditionCn;
}
