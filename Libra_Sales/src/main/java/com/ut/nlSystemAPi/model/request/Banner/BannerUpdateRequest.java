package com.ut.nlSystemAPi.model.request.Banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BannerUpdateRequest extends BannerRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}
