package com.ut.nlSystemAPi.model.request.Section;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SectionRequest {

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Long chartAccountId;

    @ApiModelProperty(position = 5)
    private Long unEarnChartAccountId;

    @ApiModelProperty(position = 6)
    private String description;

}