package com.ut.nlSystemAPi.model.entity.Promotional;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Promotional extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String title;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private Integer promotionType;

    @ApiModelProperty(position = 5)
    private Long branchId;

    @ApiModelProperty(position = 6)
    private String startDate;

    @ApiModelProperty(position = 7)
    private String endDate;

    @ApiModelProperty(position = 8)
    private Integer applyTo;
}
