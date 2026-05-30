package com.ut.nlSystemAPi.model.response.TermPrivacy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermPrivacyResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String descriptionPrivacyKh;

    @ApiModelProperty(position = 3)
    private String descriptionPrivacyEn;

    @ApiModelProperty(position = 4)
    private String descriptionTermsKh;

    @ApiModelProperty(position = 5)
    private String descriptionTermsEn;

    @ApiModelProperty(position = 6)
    private String created;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private String modified;

    @ApiModelProperty(position = 9)
    private String modifiedBy;
}
