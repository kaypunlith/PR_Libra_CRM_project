package com.ut.nlSystemAPi.model.response.Competitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CompetitorDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 6)
    private String name;

}