package com.ut.nlSystemAPi.model.entity.Quotation;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuotationStatusInformation extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

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

}
