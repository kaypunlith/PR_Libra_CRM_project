package com.ut.nlSystemAPi.model.request.TermPrivacy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermPrivacyUpdateRequest extends TermPrivacyRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
