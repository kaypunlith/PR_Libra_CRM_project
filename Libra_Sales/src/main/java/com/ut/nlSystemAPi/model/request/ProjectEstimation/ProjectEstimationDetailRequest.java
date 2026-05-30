package com.ut.nlSystemAPi.model.request.ProjectEstimation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectEstimationDetailRequest {

    @ApiModelProperty(position = 1)
    private Long projectEstimationTermId;

    @ApiModelProperty(position = 2)
    private Long vendorId;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private Double qty;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private Double unitPrice;

    @ApiModelProperty(position = 7)
    private Double totalPrice;

    @ApiModelProperty(position = 8)
    private String note;

}