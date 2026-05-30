package com.ut.nlSystemAPi.model.response.Section;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SectionResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long warehouseId;

    @ApiModelProperty(position = 4)
    private String warehouseName;

    @ApiModelProperty(position = 5)
    private Long isForSale;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String companyName;

    @ApiModelProperty(position = 5)
    private Long chartAccountId;

    @ApiModelProperty(position = 6)
    private String chartAccountName;

    @ApiModelProperty(position = 7)
    private Long unEarnChartAccountId;

    @ApiModelProperty(position = 8)
    private String unEarnChartAccountName;

    @ApiModelProperty(position = 9)
    private String description;

    @ApiModelProperty(position = 10)
    private String created;

    @ApiModelProperty(position = 11)
    private String createdBy;

    @ApiModelProperty(position = 12)
    private String modified;

    @ApiModelProperty(position = 13)
    private String modifiedBy;

    @ApiModelProperty(position = 14)
    private Long isActive;
}
