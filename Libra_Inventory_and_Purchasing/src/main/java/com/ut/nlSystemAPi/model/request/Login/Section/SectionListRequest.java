package com.ut.nlSystemAPi.model.request.Login.Section;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SectionListRequest {

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long isForSale;

}
