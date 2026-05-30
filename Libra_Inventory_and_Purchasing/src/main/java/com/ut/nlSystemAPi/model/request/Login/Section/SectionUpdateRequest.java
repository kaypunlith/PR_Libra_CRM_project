package com.ut.nlSystemAPi.model.request.Login.Section;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SectionUpdateRequest {

    @ApiModelProperty(position = 3)
    private Long id;

    @ApiModelProperty(position = 4)
    private Long warehouseId;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 4)
    private Long isForSale;

}
