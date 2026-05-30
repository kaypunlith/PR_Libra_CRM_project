package com.ut.nlSystemAPi.model.response.Promotional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PromotionalResponse {

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
    private String branchName;

    @ApiModelProperty(position = 6)
    private String startDate;

    @ApiModelProperty(position = 7)
    private String endDate;

    @ApiModelProperty(position = 8)
    private Integer applyTo;

    @ApiModelProperty(position = 9)
    private String created;

    @ApiModelProperty(position = 10)
    private String createdBy;

    @ApiModelProperty(position = 11)
    private String modified;

    @ApiModelProperty(position = 12)
    private String modifiedBy;

    @ApiModelProperty(position = 13)
    private List<PromotionalDetailResponse> details;
}
