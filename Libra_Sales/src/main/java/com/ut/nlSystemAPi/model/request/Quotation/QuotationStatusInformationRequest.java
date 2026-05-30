package com.ut.nlSystemAPi.model.request.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuotationStatusInformationRequest {

    @ApiModelProperty(position = 1)
    private Long quotationId;

    @ApiModelProperty(position = 2)
    private Long divisionId;

    @ApiModelProperty(position = 3)
    private Long quotationStatusId;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private String needName;

    @ApiModelProperty(position = 6)
    private Double expectedPercent;

    @ApiModelProperty(position = 7)
    private Long expectedWeek;

    @ApiModelProperty(position = 8)
    private Long expectedMonth;

    @ApiModelProperty(position = 9)
    private List<Long> reasons;

}
