package com.ut.nlSystemAPi.model.response.ProductGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserPgroupResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

}
