package com.ut.nlSystemAPi.model.request.BusinessActivity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusinessActivityUpdateRequest extends BusinessActivityRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}