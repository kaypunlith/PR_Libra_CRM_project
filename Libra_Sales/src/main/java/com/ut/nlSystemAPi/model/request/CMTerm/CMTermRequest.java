package com.ut.nlSystemAPi.model.request.CMTerm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CMTermRequest {
    @ApiModelProperty(position = 2)
    private String name;
}
