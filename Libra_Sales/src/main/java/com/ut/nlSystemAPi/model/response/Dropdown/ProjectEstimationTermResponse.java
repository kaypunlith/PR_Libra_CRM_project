package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectEstimationTermResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String number;

    @ApiModelProperty(position = 4)
    private String symbol;

    @ApiModelProperty(position = 5)
    private String color;

    @ApiModelProperty(position = 6)
    private Integer isMainTotal;

    @ApiModelProperty(position = 7)
    private Integer showVendor;

    @ApiModelProperty(position = 8)
    private List<ProjectEstimationTermDetailResponse> details;

}
