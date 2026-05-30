package com.ut.nlSystemAPi.model.entity.TermPrivacy;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermPrivacy extends BaseModel {

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
}
