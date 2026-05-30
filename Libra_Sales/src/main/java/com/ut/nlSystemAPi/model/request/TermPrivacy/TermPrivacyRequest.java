package com.ut.nlSystemAPi.model.request.TermPrivacy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermPrivacyRequest {

    @ApiModelProperty(position = 2)
    private String descriptionPrivacyKh;

    @ApiModelProperty(position = 3)
    private String descriptionPrivacyEn;

    @ApiModelProperty(position = 4)
    private String descriptionTermsKh;

    @ApiModelProperty(position = 5)
    private String descriptionTermsEn;
}
