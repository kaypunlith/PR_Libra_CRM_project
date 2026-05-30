package com.ut.nlSystemAPi.model.request.Login.Section;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SectionRequest {

    @ApiModelProperty(position = 3)
    private Long warehouseId;

    @ApiModelProperty(position = 4)
    private List<SectionListRequest> sections;

}
