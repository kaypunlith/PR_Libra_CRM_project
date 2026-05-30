package com.ut.nlSystemAPi.model.response.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StatusInformationResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private Long status;

    @ApiModelProperty(position = 2)
    private String statusName;

    @ApiModelProperty(position = 3)
    private Double expected;

    @ApiModelProperty(position = 4)
    private String division;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private Long expectedWeek;

    @ApiModelProperty(position = 6)
    private Long expectedMonth;

    @ApiModelProperty(position = 7)
    private String date;

    @ApiModelProperty(position = 8)
    private String createdBy;

    @ApiModelProperty(position = 9)
    private List<StatusInformationResponse> reasons;

}
