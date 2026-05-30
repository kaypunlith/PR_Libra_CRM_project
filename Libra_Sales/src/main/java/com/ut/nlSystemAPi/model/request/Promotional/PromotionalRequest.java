package com.ut.nlSystemAPi.model.request.Promotional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PromotionalRequest {

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

    @ApiModelProperty(position = 4)
    private Integer applyTo;

    @ApiModelProperty(position = 8)
    private List<PromotionalDetailRequest> details;
}
