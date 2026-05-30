package com.ut.nlSystemAPi.model.response.ProjectEstimation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectEstimationDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long projectEstimationId;

    @ApiModelProperty(position = 3)
    private Long projectEstimationTermId;

    @ApiModelProperty(position = 4)
    private String projectEstimationTermNumber;

    @ApiModelProperty(position = 5)
    private String projectEstimationTermSymbol;

    @ApiModelProperty(position = 6)
    private String projectEstimationTermName;

    @ApiModelProperty(position = 7)
    private String projectEstimationTermColor;

    @ApiModelProperty(position = 8)
    private Long vendorId;

    @ApiModelProperty(position = 9)
    private String vendorName;

    @ApiModelProperty(position = 10)
    private String description;

    @ApiModelProperty(position = 11)
    private Double qty;

    @ApiModelProperty(position = 12)
    private Long uomId;

    @ApiModelProperty(position = 13)
    private String uomName;

    @ApiModelProperty(position = 14)
    private String uomAbbr;

    @ApiModelProperty(position = 15)
    private Double unitPrice;

    @ApiModelProperty(position = 16)
    private Double totalPrice;

    @ApiModelProperty(position = 17)
    private String note;
}