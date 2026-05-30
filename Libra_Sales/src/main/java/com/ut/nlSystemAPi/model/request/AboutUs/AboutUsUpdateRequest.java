package com.ut.nlSystemAPi.model.request.AboutUs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AboutUsUpdateRequest extends AboutUsRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
